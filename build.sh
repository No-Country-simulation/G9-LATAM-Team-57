#!/usr/bin/env bash
#
# Build reproducible del MVP same-origin:
# compila Angular, copia el resultado a backend-java/src/main/resources/static
# y empaqueta el JAR de Spring Boot que contiene API + SPA.
#
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FRONTEND_DIR="$ROOT_DIR/frontend/energiai"
BACKEND_DIR="$ROOT_DIR/backend-java"
STATIC_DIR="$BACKEND_DIR/src/main/resources/static"
DIST_DIR="$FRONTEND_DIR/dist/energiai/browser"

echo "==> [1/4] Instalando dependencias y compilando Angular (production)"
(
  cd "$FRONTEND_DIR"
  npm ci --legacy-peer-deps
  npx ng build --configuration production
)

echo "==> [2/4] Limpiando directorio estático"
rm -rf "$STATIC_DIR"
mkdir -p "$STATIC_DIR"

echo "==> [3/4] Copiando build Angular a $STATIC_DIR"
cp -R "$DIST_DIR/." "$STATIC_DIR/"

echo "==> [4/4] Empaquetando JAR de Spring Boot"
(
  cd "$BACKEND_DIR"
  ./mvnw clean package
)

echo ""
echo "==> Listo. JAR generado en: $BACKEND_DIR/target/"
