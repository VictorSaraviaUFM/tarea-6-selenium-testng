# Tarea 6: Selenium y TestNG con Gradle

Pruebas de navegador contra [SauceDemo](https://www.saucedemo.com/) con Java 21, Selenium WebDriver, TestNG y Page Objects. Este fork fue migrado de Maven a Gradle.

## Requisitos

- JDK 21 (`java -version` debe indicar 21).
- Google Chrome.
- Conexión a Internet para descargar Gradle y las dependencias la primera vez, y para acceder a SauceDemo.

El proyecto incluye **Gradle Wrapper** (`gradlew`), así que no hace falta instalar Gradle por separado.

En macOS con Homebrew, JDK 21 puede activarse en la terminal actual así:

```bash
export JAVA_HOME="$(brew --prefix openjdk@21)/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
```

## Ejecutar

Desde la raíz del repositorio:

```bash
./gradlew clean test
```

Las pruebas son *headless* por defecto. Para ver Chrome y grabar la demostración:

```bash
./gradlew clean test -Dheadless=false --console=plain
```

El resultado detallado se guarda en `build/reports/tests/test/index.html`.

## Elegir las pruebas en `testng.xml`

La suite `testng.xml` incluye actualmente solo el grupo `smoke`: ejecuta dos pruebas, una de login y otra de carrito. Para ejecutar las seis pruebas, agrega `<include name="regression"/>` dentro de `<run>` y vuelve a ejecutar `./gradlew clean test`. También puedes dejar solo `regression`: las dos pruebas `smoke` pertenecen a ambos grupos.

La suite dinámica escrita en Java sigue disponible:

```bash
./gradlew dynamicSuite                         # smoke
./gradlew dynamicSuite -Drun.groups=regression # seis pruebas
```

## Qué hace el proyecto

`LoginTest` comprueba el acceso válido, el usuario bloqueado y la contraseña incorrecta. `CartTest` comprueba agregar y quitar un producto y completar una compra. `BaseTest` abre y cierra Chrome para cada prueba. Las clases en `pages/` encapsulan los elementos y las acciones de cada pantalla. TestNG organiza las pruebas en los grupos `smoke` y `regression`; Gradle compila el código, descarga las dependencias y ejecuta la suite XML.

## Entrega

Fecha límite indicada en la tarea: **15 de octubre de 2026, 23:59**.

1. Enlace a este fork actualizado en GitHub, con el proyecto en Gradle.
2. Video donde se vea el comando `./gradlew clean test -Dheadless=false --console=plain`, Chrome realizando el flujo y el resultado `BUILD SUCCESSFUL` en la terminal.
