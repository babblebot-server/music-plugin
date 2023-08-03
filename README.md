<p align="center"><img src="https://avatars.githubusercontent.com/u/138989349" alt="BabbleBot-Server" width="320" height="320" /></p>  

<h1 align="center">
   Music Plugin
</h1>

## Description

This plugin allows your bot to join a voice channel and play audio under the hood it uses 
[lavaplayer](https://github.com/Walkyst/lavaplayer-fork) which you can look at the formats it supports

**Disclaimer**: Although Lavaplayer supports YouTube and other platforms this plugin does not endorse using 
these platforms and suggests using self-hosted platforms to play audio for your bot

## Commands

| Command    | Description                                                                               | Since | Params |
|------------|-------------------------------------------------------------------------------------------|-------|--------|
| ping       | The ping command will respond with pong!                                                  | 1.0.0 | None   |
| addperson  | The add person command will add a person to the person table and respond with that entity | 1.0.0 | name*  |
| listpeople | The list people command will return all the people in the database                        | 1.0.0 | None   |
| config     | The config command will return the config for this plugin                                 | 1.0.0 | None   |

## Config

| Name      | Description               | Default | Required |
|-----------|---------------------------|---------|----------|
| someValue | Some value for the config | null    | no       |

NOTE: When developing this plugin you can set the config values using the application.properties
under the plugin prefix

## Building & Running & Packaging

To build this plugin you can run

```shell
./gradlew build
```

To Run this plugin you need to run

```shell
DISCORD_TOKEN=<token> ./gradlew runBabblebot 
```

or you can export the Discord Token first

```shell
export DISCORD_TOKEN=<token>
./gradlew runBabblebot
```

To Package the plugin you need to run

```shell
./gradlew shadowJar
```

## Using this template

To use this template you will need update a few variables inside a couple of files

Firstly rename the project to your plugin name to do this edit [GradleSettings](./settings.gradle)
do

```groovy
rootProject.name = '<project-name>'
```

Next you will need to update the [GradleBuild](./build.gradle)

1. Update the group (this is your domain)

```groovy
group '<your-group>'
```

2. Update the babblebotVersion to the latest version

```groovy
babblebotVersion = '<latest-version>'
```

Next is to change your package structure in [Here](./src/main/java)
you will need to update `com.example` to your new group name set earlier,
and then you will need to change the folder `exampleplugin` to the one set in settings.gradle (`rootProject.name`)

Finally, in [DevMain](./src/main/java/com/example/exampleplugin/DevMain.java) you need to set the
basePackages to the new basePackages you just set up

i.e

```java

@EnableJpaRepositories(basePackages = {"net.babblebot", "<your-new-package-structure>"})
public class DevMain {
}
```

## Authors

* **Ben Davies** - *Lead Developer* - [Github](https://github.com/bendavies99)
* **Aaron Burt** - *Plugin Developer* - [Github](https://github.com/aaronburt)

Currently, there is only me working on Babblebot, but it's always open for new ideas and contributions!

See also the list of [contributors](https://github.com/babblebot-server/babblebot-example-plugin/contributors) who
participated in
this project.

## License

This project is licensed under the MIT Licence - see the [LICENSE.md](LICENSE.md) file for details

