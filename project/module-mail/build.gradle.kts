dependencies {
    compileOnly(project(":project:module-common"))
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-logger"))
    compileOnly(project(":project:module-configuration"))
    compileOnly(project(":project:module-message"))
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // Java Email Library
    implementation(libs.apache.commons.email)
}

tasks.shadowJar {
    relocate("org.apache.commons", "${project.group}.libs.org.apache.commons")
}