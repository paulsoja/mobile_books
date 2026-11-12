#!/usr/bin/env bash
# The team is an Individual account, so only the App Store Connect API key can sign for it:
# archive unsigned, then sign with the team's cloud-managed distribution certificate on export.
set -euo pipefail

ASC_KEY_ID="${ASC_KEY_ID:-J48WP3D3CS}"
ASC_ISSUER_ID="${ASC_ISSUER_ID:-292f6f5f-3d0b-4e5f-b45c-2dbe99a608f6}"
ASC_KEY_PATH="${ASC_KEY_PATH:-$HOME/.appstoreconnect/private_keys/AuthKey_${ASC_KEY_ID}.p8}"
[[ -f "$ASC_KEY_PATH" ]] || { echo "API key not found: $ASC_KEY_PATH" >&2; exit 1; }

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
IOS_DIR="$(dirname "$SCRIPT_DIR")"
OUT_DIR="$IOS_DIR/build/upload"
ARCHIVE="$OUT_DIR/iosApp.xcarchive"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "==> Archiving (log: $OUT_DIR/archive.log)"
xcodebuild archive \
  -project "$IOS_DIR/iosApp.xcodeproj" \
  -scheme iosApp \
  -configuration Release \
  -destination 'generic/platform=iOS' \
  -archivePath "$ARCHIVE" \
  CODE_SIGNING_ALLOWED=NO CODE_SIGNING_REQUIRED=NO CODE_SIGN_IDENTITY= \
  > "$OUT_DIR/archive.log" 2>&1 \
  || { grep -E 'error:' "$OUT_DIR/archive.log" >&2; echo "Archive failed" >&2; exit 1; }

VERSION=$(/usr/libexec/PlistBuddy -c 'Print :ApplicationProperties:CFBundleShortVersionString' "$ARCHIVE/Info.plist")
BUILD=$(/usr/libexec/PlistBuddy -c 'Print :ApplicationProperties:CFBundleVersion' "$ARCHIVE/Info.plist")
echo "==> Archived $VERSION ($BUILD)"

if [[ "${1:-}" != "-y" ]]; then
  read -r -p "Upload $VERSION ($BUILD) to App Store Connect? [y/N] " answer
  [[ "$answer" =~ ^[Yy]$ ]] || { echo "Cancelled. Archive kept at $ARCHIVE"; exit 0; }
fi

echo "==> Signing and uploading (log: $OUT_DIR/export.log)"
xcodebuild -exportArchive \
  -archivePath "$ARCHIVE" \
  -exportOptionsPlist "$SCRIPT_DIR/ExportOptions.plist" \
  -exportPath "$OUT_DIR/export" \
  -allowProvisioningUpdates \
  -authenticationKeyPath "$ASC_KEY_PATH" \
  -authenticationKeyID "$ASC_KEY_ID" \
  -authenticationKeyIssuerID "$ASC_ISSUER_ID" \
  > "$OUT_DIR/export.log" 2>&1 \
  || { grep -A2 -E 'error:' "$OUT_DIR/export.log" >&2; echo "Upload failed" >&2; exit 1; }

echo "==> Uploaded $VERSION ($BUILD). It will appear in TestFlight after Apple finishes processing."
