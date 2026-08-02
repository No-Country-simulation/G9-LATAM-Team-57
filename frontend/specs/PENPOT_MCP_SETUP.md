# Penpot MCP - Guía de Configuración

## Resumen

El MCP (Model Context Protocol) de Penpot permite a herramientas de IA (Kiro, VS Code Copilot, Claude Desktop) interactuar directamente con archivos de diseño en Penpot. Esto nos permite crear y modificar el design system programáticamente.

## Arquitectura

```
LLM (Kiro/Copilot) ←→ MCP Server (localhost:4401) ←→ Plugin WebSocket ←→ Penpot (browser)
```

El MCP server expone tools al LLM, que se comunican con Penpot a través del plugin MCP instalado en el navegador.

## Pasos para Usar

### 1. Iniciar el MCP Server + Plugin Server

La forma más sencilla es usar `npx`:

```bash
npx -y @penpot/mcp@latest
```

Esto levanta:
- **MCP Server HTTP** en `http://localhost:4401/mcp` (Streamable HTTP)
- **MCP Server SSE** en `http://localhost:4401/sse` (Legacy SSE)
- **Plugin Server** en `http://localhost:4400` (sirve el plugin)
- **WebSocket Server** en `localhost:4402` (conexión plugin ↔ server)

### 2. Cargar el Plugin en Penpot

1. Abrir [Penpot](https://design.penpot.app) en el navegador
2. Navegar a un archivo de diseño
3. Abrir el menú de **Plugins**
4. Cargar el plugin con la URL: `http://localhost:4400/manifest.json`
5. Abrir la UI del plugin
6. Hacer clic en **"Connect to MCP server"** → el estado debe cambiar a "Connected to MCP server"

> ⚠️ **Importante**: En Chrome/Brave, al conectar desde `design.penpot.app` a `localhost`, el navegador pedirá permiso para acceso a red local. Aceptar el popup. En Brave, desactivar el "Shield" para la página de Penpot.

> ⚠️ **No cerrar la UI del plugin** mientras se usa el MCP server. Mantener la pestaña de Penpot activa (no en background).

### 3. Usar desde Kiro CLI

La configuración ya está en `.kiro/mcp.json`. Solo asegurarse de:

1. Tener el MCP server corriendo (`npx -y @penpot/mcp@latest`)
2. Tener el plugin conectado en Penpot
3. Reiniciar la sesión de Kiro para que detecte el nuevo MCP server

El server expone herramientas como:
- Consultar elementos del diseño (frames, componentes, colores, tipografías)
- Crear y modificar elementos
- Exportar assets
- Ejecutar código arbitrario en el Plugin API de Penpot

### 4. Usar desde VS Code

La configuración está en `energiai/.vscode/mcp.json`. Con GitHub Copilot Chat o extensiones MCP compatibles, se puede usar directamente con el endpoint SSE.

## Configuración Avanzada

### Variables de Entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `PENPOT_MCP_SERVER_PORT` | Puerto del servidor HTTP/SSE | `4401` |
| `PENPOT_MCP_WEBSOCKET_PORT` | Puerto WebSocket (plugin) | `4402` |
| `PENPOT_MCP_LOG_LEVEL` | Nivel de log: trace, debug, info, warn, error | `info` |
| `PENPOT_MCP_TOOL_TIMEOUT_S` | Timeout para tool calls (segundos) | `120` |

### Cambiar Puerto

```bash
PENPOT_MCP_SERVER_PORT=5000 npx -y @penpot/mcp@latest
```

## Archivos de Configuración Creados

| Archivo | Propósito |
|---------|-----------|
| `frontend/.kiro/mcp.json` | Configuración MCP para Kiro CLI (usa mcp-remote como proxy stdio→HTTP) |
| `frontend/energiai/.vscode/mcp.json` | Configuración MCP para VS Code (endpoint SSE directo) |

## Troubleshooting

- **El plugin no conecta**: Verificar que el MCP server esté corriendo y que el navegador permita acceso a localhost
- **Kiro no detecta el MCP**: Reiniciar la sesión de Kiro después de crear `.kiro/mcp.json`
- **Timeout en operaciones**: Aumentar `PENPOT_MCP_TOOL_TIMEOUT_S` si las operaciones son complejas
- **Pestaña suspendida**: En Chrome, agregar la URL de Penpot a Settings → Performance → Always keep these sites active

## Referencia

- [Penpot MCP Docs](https://github.com/penpot/penpot/tree/develop/mcp)
- [Penpot Plugin API](https://penpot.dev/plugin-api/)
- [MCP Remote (proxy stdio)](https://github.com/geelen/mcp-remote)
