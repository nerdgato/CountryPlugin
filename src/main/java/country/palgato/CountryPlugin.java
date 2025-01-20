package country.palgato;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CountryPlugin extends JavaPlugin {
    public void onEnable() {
        getLogger().info("CountryPlugin ha sido habilitado!");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI encontrado. Integrando placeholders.");
            (new CountryExpansion(this)).register();
        } else {
            getLogger().warning("PlaceholderAPI no encontrado. Algunos placeholders no funcionar");
        }
    }

    public void onDisable() {
        getLogger().info("CountryPlugin ha sido deshabilitado!");
    }
}
