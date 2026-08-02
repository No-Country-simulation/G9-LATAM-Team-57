# EnergIAi – Design System Specification for Figma AI

## Project Overview

**App Name:** EnergIAi – Inteligencia para el Consumo Energético
**Framework:** Angular 20 with Angular Material (Material Design 3)
**Purpose:** Energy consumption analysis platform using AI/ML
**Target Users:** Non-technical users analyzing home energy efficiency
**Language:** Spanish (UI labels and content)

---

## 1. Color System

### Primary Palette (Green – Sustainability)
| Token | Hex | Usage |
|-------|-----|-------|
| primary | #2E7D32 | Main actions, navbar, primary buttons |
| primary-light | #4CAF50 | Hover states, success indicators |
| primary-dark | #1B5E20 | Footer, dark surfaces |a
| primary-container | #C8E6C9 | Card backgrounds, subtle highlights |
| on-primary | #FFFFFF | Text on primary surfaces |
| on-primary-container | #1B5E20 | Text on primary-container |

### Secondary Palette (Blue – Technology)
| Token | Hex | Usage |
|-------|-----|-------|
| secondary | #1565C0 | Secondary actions, links |
| secondary-light | #2196F3 | Info badges, highlights |
| secondary-dark | #0D47A1 | Active states |
| secondary-container | #BBDEFB | Info cards background |
| on-secondary | #FFFFFF | Text on secondary surfaces |
| on-secondary-container | #0D47A1 | Text on secondary-container |

### Semantic Colors
| Token | Hex | Usage |
|-------|-----|-------|
| success / eficiente | #4CAF50 | Efficient category indicator 🟢 |
| warning / moderado | #FF9800 | Moderate category indicator 🟡 |
| error / ineficiente | #F44336 | Inefficient category indicator 🔴 |
| info | #2196F3 | Informational elements |

### Neutral Palette
| Token | Hex | Usage |
|-------|-----|-------|
| surface | #FFFFFF | Main background |
| surface-variant | #FAFAFA | Section backgrounds |
| surface-container | #F5F5F5 | Card backgrounds |
| on-surface | #212121 | Primary text |
| on-surface-variant | #616161 | Secondary text |
| outline | #E0E0E0 | Borders, dividers |
| outline-variant | #BDBDBD | Subtle borders |

---

## 2. Typography

### Font Stack
- **Primary:** Inter (headings, body, UI elements)
- **Fallback:** Roboto (Angular Material default)

### Type Scale (Material Design 3)
| Token | Font | Size | Weight | Line Height | Usage |
|-------|------|------|--------|-------------|-------|
| display-large | Inter | 40px | 700 | 48px | Hero title |
| display-medium | Inter | 32px | 700 | 40px | Page titles |
| headline-large | Inter | 28px | 600 | 36px | Section headings |
| headline-medium | Inter | 24px | 600 | 32px | Card titles |
| title-large | Inter | 20px | 600 | 28px | Form section title |
| title-medium | Inter | 16px | 500 | 24px | Subtitles |
| body-large | Inter | 16px | 400 | 24px | Main body text |
| body-medium | Inter | 14px | 400 | 20px | Secondary text |
| body-small | Inter | 12px | 400 | 16px | Captions, hints |
| label-large | Inter | 14px | 500 | 20px | Button labels |
| label-medium | Inter | 12px | 500 | 16px | Form labels |
| label-small | Inter | 11px | 500 | 16px | Helper text |

---

## 3. Spacing System

### Base Unit: 4px

| Token | Value | Usage |
|-------|-------|-------|
| spacing-xs | 4px | Inline spacing, icon gaps |
| spacing-sm | 8px | Compact element spacing |
| spacing-md | 16px | Default component padding |
| spacing-lg | 24px | Section padding, card padding |
| spacing-xl | 32px | Page section gaps |
| spacing-2xl | 48px | Major section separators |
| spacing-3xl | 64px | Hero section padding |

### Border Radius
| Token | Value | Usage |
|-------|-------|-------|
| radius-sm | 4px | Inputs, small chips |
| radius-md | 8px | Cards, buttons |
| radius-lg | 16px | Modals, featured cards |
| radius-xl | 24px | FAB, large containers |
| radius-full | 50% | Circular elements |

### Elevation (Shadows)
| Token | Value | Usage |
|-------|-------|-------|
| elevation-1 | 0 1px 3px rgba(0,0,0,0.08) | Resting cards |
| elevation-2 | 0 2px 8px rgba(0,0,0,0.10) | Hover cards |
| elevation-3 | 0 4px 16px rgba(0,0,0,0.12) | Modals, dropdowns |

---

## 4. Components

### 4.1 Navbar (MatToolbar)
- Height: 64px
- Background: linear-gradient(135deg, #2E7D32, #388E3C)
- Logo: bolt icon (28px) + "EnergIAi" (Inter 700, 1.4rem)
- Nav links: white text, 500 weight, opacity 0.85 → 1.0 on hover/active
- Sticky top, z-index 1000
- Mobile: reduce padding, smaller font

### 4.2 Footer
- Background: linear-gradient(135deg, #1B5E20, #2E7D32)
- Logo + tagline "Inteligencia para el Consumo Energético"
- Copyright text at bottom, opacity 0.7
- Padding: 24px 32px

### 4.3 Buttons

#### Primary Button (MatRaisedButton)
- Background: #2E7D32
- Text: white, Inter 500, 14px
- Border radius: 8px
- Min width: 200px
- Padding: 8px 32px
- Icon left + text
- Disabled: opacity 0.5, no pointer
- Hover: #388E3C (slightly lighter)

#### Secondary Button (MatButton)
- Border: 1px solid #2E7D32
- Text: #2E7D32
- Background: transparent
- Hover: #C8E6C9 background

### 4.4 Form Fields (MatFormField – Outline)
- Appearance: outline
- Border: 1px solid #E0E0E0
- Focus border: 2px solid #2E7D32
- Border radius: 4px
- Label: floating, 12px, #616161
- Prefix icon: 24px, #616161
- Hint text: 12px, #616161
- Error text: 12px, #F44336
- Height: 56px
- Full width within container

### 4.5 Select (MatSelect)
- Same styling as form fields
- Dropdown panel: white, elevation-3
- Option hover: #F5F5F5
- Selected option: #C8E6C9 background

### 4.6 Radio Buttons (MatRadioButton)
- Circle: 20px diameter
- Unselected: #616161 border
- Selected: #2E7D32 filled
- Label: 14px, Inter 400
- Group layout: horizontal on desktop, vertical on mobile
- Gap between options: 24px (horizontal), 8px (vertical)

### 4.7 Cards (MatCard)
- Background: #FFFFFF
- Border radius: 8px
- Padding: 24px
- Shadow: elevation-1 (resting), elevation-2 (hover)
- Transition: box-shadow 200ms ease

### 4.8 Result Card (Custom)
- Category indicator: colored circle (24px) + text
  - Eficiente: #4CAF50
  - Moderado: #FF9800
  - Ineficiente: #F44336
- Progress bar: 8px height, colored by category
- Probability: large text (32px, bold)

### 4.9 Recommendation Card (Custom)
- List with checkmark icons (✓) in #4CAF50
- Each item: 14px, Inter 400
- Spacing between items: 12px
- Left border: 3px solid #4CAF50

### 4.10 Cost Card (Custom)
- Large currency value: 32px, Inter 700, #212121
- Label "Costo estimado mensual": 14px, #616161
- Icon: attach_money, 32px, #2E7D32

### 4.11 Loading Spinner (MatProgressSpinner)
- Size: 48px
- Color: #2E7D32
- Text below: "Analizando consumo energético..." (14px, #616161)
- Centered on screen
- Fade-in animation: 200ms

### 4.12 Error Card
- Border-left: 4px solid #F44336
- Icon: error_outline, #F44336
- Message: 16px, #212121
- Button "Reintentar": outlined, #F44336

### 4.13 Snackbar (MatSnackBar)
- Position: bottom center
- Background: #212121
- Text: white, 14px
- Action button: #4CAF50 text
- Duration: 4000ms
- Border radius: 8px

---

## 5. Responsive Layouts

### Breakpoints
| Name | Range | Columns | Margins | Gutter |
|------|-------|---------|---------|--------|
| Mobile | < 768px | 4 | 16px | 16px |
| Tablet | 768px – 1199px | 8 | 24px | 24px |
| Desktop | ≥ 1200px | 12 | 32px | 24px |

### Container Max Widths
| Breakpoint | Default | Narrow (forms) | Wide (dashboard) |
|------------|---------|----------------|------------------|
| Mobile | 100% | 100% | 100% |
| Tablet | 720px | 600px | 720px |
| Desktop | 1140px | 720px | 1320px |

### Page Layouts

#### Landing Page
- **Mobile:** Single column, stacked sections, full-width hero
- **Tablet:** 2-column benefits grid, centered hero
- **Desktop:** 3-column benefits grid, max-width 1140px

#### Form Page
- **Mobile:** Single column, full-width fields, stacked radio groups
- **Tablet:** Centered card (600px max), vertical form
- **Desktop:** Centered card (720px max), vertical form

#### Results Page
- **Mobile:** Stacked cards (category → cost → recommendations)
- **Tablet:** 2-column grid (category + cost top, recommendations full width)
- **Desktop:** 3-column grid (category | cost | recommendations)

---

## 6. Angular Material Component Mapping

| Design Element | Angular Material Component | Configuration |
|----------------|---------------------------|---------------|
| Navbar | MatToolbar | color="primary" |
| Form fields | MatFormField | appearance="outline" |
| Text inputs | MatInput | type="number" |
| Dropdowns | MatSelect + MatOption | — |
| Radio groups | MatRadioGroup + MatRadioButton | — |
| Primary button | MatButton | mat-raised-button, color="primary" |
| Cards | MatCard | MatCardHeader, MatCardContent |
| Spinner | MatProgressSpinner | mode="indeterminate" |
| Notifications | MatSnackBar | duration: 4000 |
| Icons | MatIcon | Material Icons font |
| Progress bar | MatProgressBar | mode="determinate" |

### Theme Configuration (styles.scss)
```scss
@use '@angular/material' as mat;

html {
  @include mat.theme((
    color: (
      primary: mat.$green-palette,
      tertiary: mat.$blue-palette,
    ),
    typography: Roboto,
    density: 0,
  ));
}
```

---

## 7. Accessibility (WCAG 2.1 AA)

### Color Contrast
| Combination | Ratio | Status |
|-------------|-------|--------|
| White on #2E7D32 (primary) | 4.87:1 | ✅ AA |
| White on #1B5E20 (dark) | 7.24:1 | ✅ AAA |
| #212121 on #FFFFFF (text) | 16.1:1 | ✅ AAA |
| #616161 on #FFFFFF (secondary text) | 5.91:1 | ✅ AA |
| White on #F44336 (error) | 4.02:1 | ✅ AA |
| #212121 on #FAFAFA (surface) | 15.4:1 | ✅ AAA |

### Form Accessibility
- Every input must have a visible `<mat-label>`
- Every input must have a `mat-hint` for context
- Error messages: `<mat-error>` linked via aria-describedby (automatic in Angular Material)
- Required fields: asterisk indicator
- Focus visible: 2px solid #2E7D32 outline
- Tab order: logical top-to-bottom flow

### Interactive Elements
- Buttons: min touch target 44x44px
- Radio buttons: min touch target 44x44px
- Focus ring: visible 2px outline on keyboard navigation
- Skip-to-content link: hidden visually, available to screen readers
- Language: `<html lang="es">`

### Screen Reader Support
- Semantic headings hierarchy (h1 → h2 → h3)
- aria-live="polite" on result updates
- aria-busy="true" during loading
- Role="alert" on error messages
- Meaningful alt text on icons (aria-label)

---

## 8. Animations & Transitions

| Element | Animation | Duration | Easing |
|---------|-----------|----------|--------|
| Page transitions | Fade in | 200ms | ease-in-out |
| Card hover | Shadow increase | 200ms | ease |
| Button hover | Background lighten | 150ms | ease |
| Spinner | Rotate | continuous | linear |
| Error appear | Slide down + fade | 200ms | ease-out |
| Results cards | Stagger fade-in | 200ms each, 100ms delay | ease-in |

---

## 9. Iconography

### Source: Material Icons (Google Fonts)

| Context | Icon Name | Usage |
|---------|-----------|-------|
| Logo/Brand | bolt | Navbar, footer |
| Home | home | Housing type |
| People | people | Household size |
| AC | ac_unit | Air conditioning |
| Laptop | laptop | Home office |
| Devices | devices | Equipment count |
| Energy | electric_meter | Consumption |
| Schedule | schedule | Peak usage hours |
| Analysis | analytics | Submit button |
| Success | check_circle | Recommendations |
| Error | error_outline | Error states |
| Money | attach_money | Cost estimate |
| Chart | bar_chart | Category indicator |
| Refresh | refresh | New analysis |
| Leaf | eco | Sustainability benefits |
| Light | lightbulb | Tips/insights |

---

## 10. Page Designs (Figma Frames)

### Frame 1: Landing Page (Desktop 1440px)
```
┌─────────────────────────────────────────────────┐
│ [Navbar] ⚡ EnergIAi          Inicio | Analizar │
├─────────────────────────────────────────────────┤
│                                                 │
│     "Analiza tu consumo energético              │
│      en segundos"                               │
│                                                 │
│     Subtitle text explaining the app            │
│                                                 │
│         [ Comenzar análisis → ]                 │
│                                                 │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐        │
│  │⚡       │  │🌱       │  │💰       │        │
│  │Analiza  │  │Reduce   │  │Estima   │        │
│  │consumo  │  │desper-  │  │costos   │        │
│  │         │  │dicios   │  │         │        │
│  └─────────┘  └─────────┘  └─────────┘        │
│                                                 │
├─────────────────────────────────────────────────┤
│ [Footer] ⚡ EnergIAi | © 2026                  │
└─────────────────────────────────────────────────┘
```

### Frame 2: Form Page (Desktop 1440px)
```
┌─────────────────────────────────────────────────┐
│ [Navbar]                                        │
├─────────────────────────────────────────────────┤
│                                                 │
│    ┌──────────────────────────────────┐         │
│    │ ⚡ Información General           │         │
│    │ Complete los datos de su vivienda│         │
│    │                                  │         │
│    │ 👥 Personas en la vivienda       │         │
│    │ [___4___]                         │         │
│    │                                  │         │
│    │ 🏠 Tipo de vivienda             │         │
│    │ [▼ Casa          ]               │         │
│    │                                  │         │
│    │ ❄️ Aire acondicionado           │         │
│    │ (●) Sí   ( ) No                 │         │
│    │                                  │         │
│    │ 💻 Home Office                  │         │
│    │ (●) Sí   ( ) No                 │         │
│    │                                  │         │
│    │ 📱 Equipos eléctricos           │         │
│    │ [___10___]                        │         │
│    │                                  │         │
│    │ ⚡ Consumo último recibo    kWh  │         │
│    │ [___420___]                       │         │
│    │                                  │         │
│    │ 🕐 Horas uso simultáneo         │         │
│    │ ( ) 0-2h  (●) 3-5h  ( ) >5h    │         │
│    │                                  │         │
│    │     [ 🔍 Analizar consumo ]      │         │
│    └──────────────────────────────────┘         │
│                                                 │
├─────────────────────────────────────────────────┤
│ [Footer]                                        │
└─────────────────────────────────────────────────┘
```

### Frame 3: Results Page (Desktop 1440px)
```
┌─────────────────────────────────────────────────┐
│ [Navbar]                                        │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌──────────┐ ┌──────────┐ ┌────────────────┐  │
│  │ Categoría│ │  Costo   │ │Recomendaciones │  │
│  │          │ │ Estimado │ │                │  │
│  │  🟡     │ │          │ │ ✓ Reducir uso  │  │
│  │Moderado  │ │  $315    │ │   horario pico │  │
│  │          │ │  /mes    │ │                │  │
│  │ ████░░  │ │          │ │ ✓ Revisar      │  │
│  │  82%     │ │          │ │   equipos      │  │
│  │          │ │          │ │                │  │
│  └──────────┘ └──────────┘ │ ✓ Distribuir   │  │
│                             │   actividades  │  │
│                             └────────────────┘  │
│                                                 │
│         [ 🔄 Nuevo análisis ]                   │
│                                                 │
├─────────────────────────────────────────────────┤
│ [Footer]                                        │
└─────────────────────────────────────────────────┘
```

### Frame 4: Loading State
```
┌─────────────────────────────────────────────────┐
│ [Navbar]                                        │
├─────────────────────────────────────────────────┤
│                                                 │
│                                                 │
│              🔄 (spinner)                       │
│                                                 │
│     "Analizando consumo energético..."          │
│                                                 │
│                                                 │
├─────────────────────────────────────────────────┤
│ [Footer]                                        │
└─────────────────────────────────────────────────┘
```

### Frame 5: Error State
```
┌─────────────────────────────────────────────────┐
│ [Navbar]                                        │
├─────────────────────────────────────────────────┤
│                                                 │
│    ┌──────────────────────────────────┐         │
│    │ ⚠️ Error                         │         │
│    │                                  │         │
│    │ No fue posible realizar el       │         │
│    │ análisis. Intente nuevamente.    │         │
│    │                                  │         │
│    │        [ Reintentar ]            │         │
│    └──────────────────────────────────┘         │
│                                                 │
├─────────────────────────────────────────────────┤
│ [Footer]                                        │
└─────────────────────────────────────────────────┘
```

### Frame 6: Mobile Versions (375px)
Create mobile versions of all frames above with:
- Single column layout
- Full-width cards
- Stacked radio buttons (vertical)
- Hamburger menu (optional) or compact nav
- Bottom-aligned CTA buttons
- Touch-friendly tap targets (44px min)

---

## 11. Design Tokens Summary (for Figma Variables)

### Color Tokens
```
--md-sys-color-primary: #2E7D32
--md-sys-color-on-primary: #FFFFFF
--md-sys-color-primary-container: #C8E6C9
--md-sys-color-secondary: #1565C0
--md-sys-color-on-secondary: #FFFFFF
--md-sys-color-error: #F44336
--md-sys-color-on-error: #FFFFFF
--md-sys-color-surface: #FFFFFF
--md-sys-color-on-surface: #212121
--md-sys-color-outline: #E0E0E0
--energiai-eficiente: #4CAF50
--energiai-moderado: #FF9800
--energiai-ineficiente: #F44336
```

### Spacing Tokens
```
--spacing-xs: 4px
--spacing-sm: 8px
--spacing-md: 16px
--spacing-lg: 24px
--spacing-xl: 32px
--spacing-2xl: 48px
--spacing-3xl: 64px
```

### Radius Tokens
```
--radius-sm: 4px
--radius-md: 8px
--radius-lg: 16px
--radius-xl: 24px
```

---

## 12. Figma File Structure

Organize the Figma file with these pages:

1. **🎨 Design Tokens** – Colors, typography, spacing, radius, elevation
2. **🧩 Components** – All reusable components with variants
3. **📐 Layouts** – Desktop, Tablet, Mobile grids
4. **📱 Pages – Mobile** – All screens at 375px
5. **💻 Pages – Desktop** – All screens at 1440px
6. **📊 Pages – Tablet** – All screens at 768px
7. **🔄 States** – Loading, error, empty, success states
8. **♿ Accessibility** – Contrast checks, focus states, touch targets

### Component Variants to Create
For each component, create variants for:
- **State:** Default, Hover, Focus, Active, Disabled, Error
- **Size:** Small, Medium, Large (where applicable)
- **Theme:** Light (primary delivery)
- **Device:** Desktop, Mobile (where layout differs)


# Sprints del DESIGN SYSTEM
## Sprint 1
Create Foundations.

Generate:

Color Tokens

Typography Tokens

Spacing

Radius

Elevation

Variables

Use Material Design 3.

Do not create screens.

## Sprint 2
Create Component Library.

Generate:

Buttons

Inputs

Selects

Cards

Navbar

Footer

Dialogs

Snackbar

Badges

Recommendation Card

Cost Card

Loading

Error

Everything must be reusable.

Use Variants.

## Sprint 3
Create Layout System.

Desktop

Tablet

Mobile

Grid

Breakpoints

Containers

Margins

## Sprint 4
Build application screens using ONLY the existing components.

Landing

Analysis

Results

Loading

Error

Do not create new components.