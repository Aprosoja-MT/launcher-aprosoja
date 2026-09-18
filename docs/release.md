# Release do AproLauncher

## Versionamento

A versao fica em um unico lugar: `version.properties` na raiz do projeto.

```properties
versionName=1.0.1
```

O `versionCode` e derivado automaticamente pelo `build.gradle`:

`versionCode = MAJOR * 1000000 + MINOR * 10000 + PATCH * 100`

Exemplos: `1.0.1` -> `1000100`, `1.2.0` -> `1020000`, `2.0.0` -> `2000000`.

Para um reenvio da mesma versao (rebuild pontual) existe a propriedade opcional
`-PappVersionBuild=1` (0..99), que ocupa os dois ultimos digitos do `versionCode`.
Para sobrescrever a versao em uma build local: `-PappVersionName=1.3.0`.

Builds de debug usam o nome `1.0.1-dev.<commit>` (ou `1.0.1-dev.ci.<run>` no CI) e nao
alteram o arquivo de versao.

## Publicar uma nova versao

1. Acesse **Actions -> Release -> Run workflow** no GitHub.
2. Preencha `version` com a versao desejada (ex.: `1.1.0`) ou deixe em branco para
   incrementar o patch automaticamente.
3. Marque `prerelease` se for uma versao de teste.

O workflow executa, nesta ordem:

- valida os segredos de assinatura;
- grava a nova versao em `version.properties`;
- confere que a tag ainda nao existe;
- gera o APK assinado `AproLauncher-<versao>-release.apk`;
- cria o commit `chore(release): v<versao>` e a tag `v<versao>`;
- publica a release no GitHub com o APK anexado e as notas geradas a partir dos PRs;
- guarda o `mapping.txt` do R8 como artefato por 90 dias.

O download do APK fica em `https://github.com/Aprosoja-MT/launcher-aprosoja/releases/latest`.

## Assinatura

A frota e distribuida por Knox, fora da Play Store, e os aparelhos em campo receberam
APKs assinados com a debug keystore da maquina de desenvolvimento
(`~/.android/debug.keystore`, SHA-256 `DD:91:77:D6:...:B4:B4:F9`, valida ate 2056).

O Android so aceita atualizar um app quando a assinatura e identica a da versao ja
instalada. Cada runner do GitHub Actions gera uma debug keystore propria e aleatoria,
entao o CI precisa receber exatamente essa chave; caso contrario a instalacao falha com
`INSTALL_FAILED_UPDATE_INCOMPATIBLE` e so resta desinstalar e reinstalar, perdendo os
dados locais do app.

Segredos do repositorio (Settings -> Secrets and variables -> Actions):

| Segredo | Conteudo atual |
| --- | --- |
| `KEYSTORE` | saida de `base64 -i ~/.android/debug.keystore` |
| `KEYSTORE_PASSWORD` | `android` |
| `KEY_ALIAS` | `androiddebugkey` |
| `KEY_PASSWORD` | `android` |

O arquivo `~/.android/debug.keystore` passa a ser insubstituivel: sem ele nao ha como
atualizar os aparelhos ja instalados. Mantenha uma copia no cofre de senhas da equipe.

Migrar para uma keystore dedicada exige desinstalar e reinstalar o app em toda a frota
via Knox na mesma janela.

Conferir com qual chave um APK foi assinado:

```bash
$ANDROID_HOME/build-tools/36.1.0/apksigner verify --print-certs caminho/do.apk
```

## Bump local

```bash
scripts/bump-version.sh        # incrementa o patch
scripts/bump-version.sh 1.2.0  # define a versao
```

## CI

O workflow `CI` roda em pushes para `aprolauncher-dev` e em pull requests que apontam
para essa branch: verifica o estilo (`spotlessCheck`) e gera o APK de debug como
artefato por 14 dias.
