# JavaFX on Java Platform

## Introduction

This is a template project for the development of a desktop application:

* Java Platform
* JavaFX GUI Framework

It is the companion project of

* https://github.com/future2r/potato
* https://github.com/future2r/gelato

## Prerequisites

Install VS Code:

    winget install --id Microsoft.VisualStudioCode

Install VS Code extensions:

    code --install-extension vscjava.vscode-java-pack
    code --install-extension redhat.vscode-xml

Install Java Development Kit (JDK):

    winget install --id Microsoft.OpenJDK.25

Download Apache Maven (required only one time):

* https://maven.apache.org

## Workspace

Open the workspace in VS Code:

    File > Open Workspace from File... > Tomato.code-workspace

This workspace contains editor settings, Maven configuration and extension recommendations.

Configure JDK in your user settings:

    "java.jdt.ls.java.home": "C:/Program Files/Microsoft/jdk-25.0.2.10-hotspot",
    "java.home": "C:/Program Files/Microsoft/jdk-25.0.2.10-hotspot",
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-25",
            "path": "C:/Program Files/Microsoft/jdk-25.0.2.10-hotspot"
        }
    ]

Three debug launch configurations are available (F5):

* **Tomato** - Default locale
* **Tomato (English)** - English locale
* **Tomato (German)** - German locale

## Build, Test and Run

Build the project:

    ./mvnw clean package

Run the tests:

    ./mvnw clean test

Run the application from command line (requires `JAVA_HOME` to be set and a prior build):

    Tomato.cmd

Alternatively, run the application from VS Code using one of the launch configurations (F5).

## Project Structure

The project is organized as a multi-module Maven project:

* `core/` - Core module with domain model (`Variety`) and in-memory database (`Database`)
* `gui/` - GUI module with JavaFX application, MVVM views and controllers
    * `src/main/resources/.../i18n/` - Localization (English, German)
    * `src/main/resources/.../image/` - Application icons (PNG)
    * `src/main/resources/.../view/` - FXML layouts and CSS stylesheets
    * `src/test/` - Unit tests (JUnit)
* `src/design/` - Design assets (ICO and PNG in various sizes)

## Project Setup

These steps document how the project was created from scratch.

In VS Code terminal, initialize Maven Wrapper:

    $env:JAVA_HOME = "C:/Program Files/Microsoft/jdk-25.0.2.10-hotspot"
    ./apache-maven-3.9.13/bin/mvn wrapper:wrapper

After this, you can delete Maven and use `mvnw` to run it.
If you start Maven commands from within VS Code, make sure the setting `maven.terminal.useJavaHome` is set in the workspace.
If you run Maven from plain terminal, make sure `JAVA_HOME` is permanently set.
