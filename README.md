# Updater

Is a library that monitors whether there is an update for the project using the modrinth API.

### Requirements
- Java 17 or higher

### Installation

> 1. Add the repository to your project.

**Maven**:

```xml
<repository>
  <id>felipe-releases</id>
  <name>Felipe Ros</name>
  <url>https://repo.felipe.fun/releases</url>
</repository>
```

**Gradle (Groovy)**:

```groovy
maven {
    url "https://repo.felipe.fun/releases"
}
```

**Gradle (Kotlin)**:

```kotlin
maven {
    url = uri("https://repo.felipe.fun/releases")
}
```
> 2. Add the dependency.

**Maven**:

```xml
<dependency>
  <groupId>fun.felipe</groupId>
  <artifactId>Updater</artifactId>
  <version>1.0-RELEASE</version>
</dependency>
```

**Gradle (Groovy)**:

```groovy
implementation "fun.felipe:Updater:1.0-RELEASE"
```

**Gradle (Kotlin)**:

```kotlin
implementation("fun.felipe:Updater:1.0-RELEASE")
```

### Usage/Example
```java
//In your Main class.
@Override
public void onEnable() {
    if (!Updater.verification("project modrinth slug", this.plgetPluginMeta().getVersion()) {
      this.getLogger().warning("There is a new version available!");
    }
}

