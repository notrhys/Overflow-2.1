package us.overflow.anticheat.config.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.OverflowPlugin;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.impl.aimassist.*;
import us.overflow.anticheat.check.impl.autoclicker.AutoClickerA;
import us.overflow.anticheat.check.impl.autoclicker.AutoClickerB;
import us.overflow.anticheat.check.impl.autoclicker.AutoClickerD;
import us.overflow.anticheat.check.impl.autoclicker.AutoClickerE;
import us.overflow.anticheat.check.impl.autotool.AutoTool;
import us.overflow.anticheat.check.impl.badpackets.*;
import us.overflow.anticheat.check.impl.flight.FlightA;
import us.overflow.anticheat.check.impl.flight.FlightB;
import us.overflow.anticheat.check.impl.flight.FlightC;
import us.overflow.anticheat.check.impl.flight.FlightD;
import us.overflow.anticheat.check.impl.invalid.InvalidA;
import us.overflow.anticheat.check.impl.invalid.InvalidB;
import us.overflow.anticheat.check.impl.invalidposition.InvalidPosition;
import us.overflow.anticheat.check.impl.invalidrotation.InvalidRotation;
import us.overflow.anticheat.check.impl.killaura.*;
import us.overflow.anticheat.check.impl.motion.MotionA;
import us.overflow.anticheat.check.impl.motion.MotionB;
import us.overflow.anticheat.check.impl.motion.MotionC;
import us.overflow.anticheat.check.impl.motion.MotionD;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldA;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldB;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldC;
import us.overflow.anticheat.check.impl.speed.Speed;
import us.overflow.anticheat.check.impl.timer.TimerA;
import us.overflow.anticheat.config.type.Config;

import java.io.File;
import java.util.Arrays;

public final class CheckConfig implements Config {

    private File file;
    private YamlConfiguration config;

    private final Class[] checkClasses = new Class[] {
            MotionA.class, MotionB.class, MotionC.class, MotionD.class,
            FlightA.class, FlightB.class, FlightC.class, FlightD.class,
            KillAuraA.class, KillAuraB.class, KillAuraC.class, KillAuraD.class, KillAuraE.class, KillAuraF.class, KillAuraG.class,
            AutoClickerA.class, AutoClickerB.class, AutoClickerE.class, AutoClickerD.class, AutoClickerE.class,
            AimAssistA.class, AimAssistB.class, AimAssistC.class, AimAssistD.class, AimAssistE.class,
            BadPacketsA.class, BadPacketsB.class, BadPacketsC.class, BadPacketsD.class, BadPacketsE.class, BadPacketsF.class, BadPacketsG.class,
            ScaffoldA.class, ScaffoldB.class, ScaffoldC.class,
            InvalidA.class, InvalidB.class,
            Speed.class,
            TimerA.class,
            AutoTool.class,
            InvalidPosition.class, InvalidRotation.class,
    };

    @Override
    public void generate() {
        this.create();

        Arrays.stream(checkClasses).filter(check -> config.contains("checks." + check.getSimpleName().toLowerCase())).forEach(check -> {
            config.set("checks." + check.getSimpleName().toLowerCase() + ".enabled", true);
            config.set("checks." + check.getSimpleName().toLowerCase() + ".autoban", true);
            config.set("check." + check.getSimpleName().toLowerCase() + ".threshold", 10);
            config.set("checks." + check.getSimpleName().toLowerCase() + ".punishment", "ban %player% [OverFlow] Cheating -s");
        });

        try {
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create() {
        final OverflowPlugin plugin = OverflowAPI.INSTANCE.getPlugin();

        this.file = new File(plugin.getDataFolder(), "checks.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();

                plugin.saveResource("checks.yml", false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.config = new YamlConfiguration();

        try {
            config.load(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getCheckEnabled(final Check check) {
        return config.getBoolean("checks." + check.getClass().getSimpleName().toLowerCase() + ".enabled");
    }

    public boolean getCheckAutoban(final Check check) {
        return config.getBoolean("checks." + check.getClass().getSimpleName().toLowerCase() + ".autoban");
    }

    public String getPunishment(final Check check) {
        return config.getString("checks." + check.getClass().getSimpleName().toLowerCase() + ".punishment");
    }

    public int getThreshold(final Check check) {
        return config.getInt("checks." + check.getClass().getSimpleName().toLowerCase() + ".threshold");
    }
}
