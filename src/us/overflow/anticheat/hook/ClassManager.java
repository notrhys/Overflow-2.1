package us.overflow.anticheat.hook;

import lombok.Getter;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.type.CheckManager;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.http.HTTPUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
@Getter
public class ClassManager {

    private static ClassManager classManager;

    private List<String> resolvedStrings = new CopyOnWriteArrayList<>();

    public boolean flag = false;

    public ClassManager() {
        classManager = this;

    }

    public void start() {
        MathUtil.getClickVariance(System.currentTimeMillis() / ThreadLocalRandom.current().nextInt(99999));

        OverflowAPI.INSTANCE.getAuthExecutor().scheduleAtFixedRate(() -> {
            MathUtil.getClickVariance(System.currentTimeMillis() / ThreadLocalRandom.current().nextInt(99999));

            if (flag) {
                resolvedStrings.clear();
            }
        }, 20L, 20L, TimeUnit.SECONDS);

        resolvePaths();
    }

    private void resolvePaths() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("SS", "G^0IfhFmv81AuOMvvfQofKs^DEdielHv^0D3DUu@t3");
        headers.put("TYPE", "3094R09324892305843509");
        String data = HTTPUtil.getResponse("http://service.overflowac.pw/resolve.php", headers);
        resolvedStrings.add(data);
        System.out.println("debug class: " + data);
        this.setup();
    }

    private void setup() {
        String path = resolveString(0);
        try {
            Class.forName(path).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String resolveString(int i) {
        return resolvedStrings.get(i);
    }
}
