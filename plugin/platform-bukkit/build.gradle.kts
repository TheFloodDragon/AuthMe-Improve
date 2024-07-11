description = "Fork of the first authentication plugin for the Bukkit API!"

dependencies {

    // Adventure Bukkit
    implementation(libs.adventure.platform.bukkit)

    // Hooks - Start
    // bStats metrics
    implementation("org.bstats:bstats-bukkit:3.0.2")
    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    // LuckPerms plugin
    compileOnly("net.luckperms:api:5.4")
    // PermissionsEx plugin
    compileOnly("ru.tehkode:PermissionsEx:1.23.5-SNAPSHOT")
    // Hooks - End

    // TODO It's necessary?
    compileOnly(libs.apache.commons.email)

}

tasks.shadowJar {
    archiveBaseName.set("AuthMe-Bukkit")
}