package us.overflow.anticheat.utils.http;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.OSUtils;
import us.overflow.anticheat.utils.Verbose;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;

public class ALAPI {

    private String licenseKey;
    private Plugin plugin;
    private String validationServer;
    private LogType logType = LogType.NORMAL;
    private String securityKey = "TaMiSCENELCraDiAnAZonVeNbsONyMESMaNC";
    private boolean debug = false, ran;

    private static String sKeyCache, randCache, keyCache;

    public ALAPI(String licenseKey, String validationServer, Plugin plugin) {
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }

    public ALAPI setConsoleLog(LogType logType) {
        this.logType = logType;
        return this;
    }

    public boolean register() {
        ValidationType vt = isValid();
        if (vt == ValidationType.VALID) {
            MathUtil.hasChecked = true;
            return true;
        } else {
            MathUtil.error = true;
            Bukkit.getScheduler().cancelTasks(plugin);
            return false;
        }
    }

    public ValidationType isValid() {


        if ((System.getProperty(toBinary(OverflowAPI.INSTANCE.getKey()) + toBinary(OverflowAPI.INSTANCE.getKey() )) == null || !System.getProperty(toBinary(OverflowAPI.INSTANCE.getKey()) + toBinary(OverflowAPI.INSTANCE.getKey())).equalsIgnoreCase(toBinary(OverflowAPI.INSTANCE.getKey())))) {
          //  return ValidationType.KEY_NOT_FOUND;
        }

        String rand = toBinary(UUID.randomUUID().toString());
        String sKey = toBinary(securityKey);
        String key = toBinary(licenseKey);
        try {
            URLConnection url = new URL(validationServer + "?v1=" + xor(rand, sKey) + "&v2=" + xor(rand, key) + "&pl=" + plugin.getName()).openConnection();
            url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            url.connect();
            if (!ran) {
                updateClientName(sKey, rand, key);
                ran = true;
            }
            sKeyCache = sKey;
            randCache = rand;
            keyCache = key;

            Scanner s = new Scanner(url.getInputStream());
            if (s.hasNext()) {
                String response = s.next();
                s.close();
                if (OSUtils.getPlatform() == OSUtils.OS.LINUX) {
                    try {
                        return ValidationType.valueOf(response);
                    } catch (IllegalArgumentException exc) {
                        String respRand = xor(xor(response, key), sKey);
                        if (rand.substring(0, respRand.length()).equals(respRand)) return ValidationType.VALID;
                        else return ValidationType.WRONG_RESPONSE;
                    }
                } else {
                    logUserRee();
                    return ValidationType.KEY_NOT_FOUND;
                }
            } else {
                s.close();
                return ValidationType.PAGE_ERROR;
            }
        } catch (IOException exc) {
            return ValidationType.URL_ERROR;
        }
    }

    private void updateClientName(String sKey, String rand, String key) {
        String in = HTTPUtil.getResponse("http://51.38.113.121/Panel/userName.php" + "?v1=" + xor(rand, sKey) + "&v2=" + xor(rand, key) + "&pl=Overflow");
        if (!in.contains("[ERROR]")) Verbose.licensedTo = in;
    }


    public static void logUserRee() {
        OverflowAPI.INSTANCE.getAuthExecutor().execute(() -> HTTPUtil.getResponse("http://51.38.113.121/Panel/logUserGay.php" + "?v1=" + xor(randCache, sKeyCache) + "&v2=" + xor(randCache, keyCache) + "&pl=Overflow"));
    }

    private static String xor(String s1, String s2) {
        String s0 = "";
        for (int i = 0; i < (s1.length() < s2.length() ? s1.length() : s2.length()); i++)
            s0 += Byte.valueOf("" + s1.charAt(i)) ^ Byte.valueOf("" + s2.charAt(i));
        return s0;
    }

    public enum LogType {
        NORMAL, LOW, NONE;
    }

    public enum ValidationType {
        WRONG_RESPONSE, PAGE_ERROR, URL_ERROR, KEY_OUTDATED, KEY_NOT_FOUND, NOT_VALID_IP, INVALID_PLUGIN, VALID;
    }

    public static String toBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }
}
