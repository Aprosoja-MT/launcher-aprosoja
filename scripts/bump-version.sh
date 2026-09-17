#!/usr/bin/env bash
set -euo pipefail

readonly VERSION_FILE="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)/version.properties"
readonly VERSION_PATTERN='^[0-9]+\.[0-9]+\.[0-9]+$'

current=$(sed -n 's/^versionName=//p' "$VERSION_FILE" | tr -d '[:space:]')

if [[ ! "$current" =~ $VERSION_PATTERN ]]; then
  echo "versionName invalido em $VERSION_FILE: '$current'" >&2
  exit 1
fi

requested="${1:-}"

if [[ -z "$requested" ]]; then
  IFS=. read -r major minor patch <<<"$current"
  next="${major}.${minor}.$((patch + 1))"
else
  next="$requested"
fi

if [[ ! "$next" =~ $VERSION_PATTERN ]]; then
  echo "versao invalida: '$next' (esperado MAJOR.MINOR.PATCH)" >&2
  exit 1
fi

if [[ "$next" == "$current" || "$next" != "$(printf '%s\n%s\n' "$current" "$next" | sort -V | tail -1)" ]]; then
  echo "versao '$next' nao e maior que a atual '$current'" >&2
  exit 1
fi

printf 'versionName=%s\n' "$next" > "$VERSION_FILE"
printf '%s\n' "$next"
