# Mentorship Content DSL

This project uses a small custom DSL for book content.\
The DSL is intentionally limited and predictable, so it is easy to parse
and render in the app.

The goal is not to support full Markdown or HTML.\
The goal is to support only the content structures and text styles that
are actually needed in the app.

------------------------------------------------------------------------

## File structure

Each book is stored separately.

Example:

books/born_again_1_en/meta.json week1.md week2.md ... week10.md

Each `weekN.md` file contains one full week with all lessons inside it.

------------------------------------------------------------------------

## Document structure

A week file consists of:

1.  front matter
2.  week header
3.  lesson sections
4.  lesson content blocks

------------------------------------------------------------------------

## Supported block-level syntax

-   Front matter (`--- ... ---`)
-   Week header (`# Week ...`)
-   Lesson header (`## Lesson ...`)
-   Quote (`> ...`)
-   Paragraphs
-   Divider (`---`)
-   CenterText (`center: ...`)
-   Image placeholder (`image: path`)
-   Table placeholder (`table`)

------------------------------------------------------------------------

## Supported inline-level syntax

-   `<b>`{=html}bold`</b>`{=html}
-   `<i>`{=html}italic`</i>`{=html}
-   `<u>`{=html}underline`</u>`{=html}
-   `<mark>`{=html}highlight`</mark>`{=html}

Supports nesting and combinations.

------------------------------------------------------------------------

## Design principles

-   Small and explicit
-   Stable and predictable
-   Rich text without full HTML
