package country.palgato;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;

public class CountryExpansion extends PlaceholderExpansion {
    private final CountryPlugin plugin;

    public CountryExpansion(CountryPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "TuNombre";
    }

    @Override
    public String getIdentifier() {
        return "country";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        Player targetPlayer = Bukkit.getPlayer(identifier);
        if (targetPlayer != null && targetPlayer.isOnline()) {
            return this.getCountryCode(targetPlayer);
        }

        return null; // Retorna null si no encuentra el jugador
    }

    private String getCountryCode(Player player) {
        try {
            String ip = player.getAddress().getAddress().getHostAddress();
            String apiKey = plugin.getConfig().getString("api-key");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("La clave de la API no está configurada.");
            }

            URL url = new URL("http://api.ipstack.com/" + ip + "?access_key=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer content = new StringBuffer();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();
                JSONObject data = new JSONObject(content.toString());
                String countryCode = data.getString("country_code");
                return toSmallCaps(countryCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private String toSmallCaps(String input) {
        StringBuilder smallCaps = new StringBuilder();
        for (char c : input.toUpperCase().toCharArray()) {
            switch (c) {
                case 'A': smallCaps.append('ᴀ'); break;
                case 'B': smallCaps.append('ʙ'); break;
                case 'C': smallCaps.append('ᴄ'); break;
                case 'D': smallCaps.append('ᴅ'); break;
                case 'E': smallCaps.append('ᴇ'); break;
                case 'F': smallCaps.append('ꜰ'); break;
                case 'G': smallCaps.append('ɢ'); break;
                case 'H': smallCaps.append('ʜ'); break;
                case 'I': smallCaps.append('ɪ'); break;
                case 'J': smallCaps.append('ᴊ'); break;
                case 'K': smallCaps.append('ᴋ'); break;
                case 'L': smallCaps.append('ʟ'); break;
                case 'M': smallCaps.append('ᴍ'); break;
                case 'N': smallCaps.append('ɴ'); break;
                case 'O': smallCaps.append('ᴏ'); break;
                case 'P': smallCaps.append('ᴘ'); break;
                case 'Q': smallCaps.append('ǫ'); break;
                case 'R': smallCaps.append('ʀ'); break;
                case 'S': smallCaps.append('ꜱ'); break;
                case 'T': smallCaps.append('ᴛ'); break;
                case 'U': smallCaps.append('ᴜ'); break;
                case 'V': smallCaps.append('ᴠ'); break;
                case 'W': smallCaps.append('ᴡ'); break;
                case 'X': smallCaps.append('x'); break;
                case 'Y': smallCaps.append('ʏ'); break;
                case 'Z': smallCaps.append('ᴢ'); break;
                default: smallCaps.append(c);
            }
        }
        return smallCaps.toString();
    }
}
