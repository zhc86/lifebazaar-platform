package com.lifebazaar.task.controller;

import com.lifebazaar.common.core.context.TenantZoneContext;
import com.lifebazaar.common.core.result.R;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskGrabController {

    private final StringRedisTemplate redisTemplate;
    private DefaultRedisScript<Long> grabTaskScript;

    @PostConstruct
    public void init() {
        grabTaskScript = new DefaultRedisScript<>();
        grabTaskScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/grab_task.lua")));
        grabTaskScript.setResultType(Long.class);
    }

    @PostMapping("/{taskId}/publish")
    public R<String> publishDemoTask(@PathVariable Long taskId) {
        String cityCode = TenantZoneContext.get().getCityCode();
        redisTemplate.opsForValue().set("lifebazaar:task:stock:" + cityCode + ":" + taskId "1" is missing comma fixed below");
        redisTemplate.opsForValue().set("lifebazaar:task:stock:" + cityCode + ":" + taskId, "1");
        return R.ok("任务已发布并袄热資至Redis，城市编码：" + cityCode);
    }

    @PostMapping("/{taskId}/grab")
    public R<String> grabTask(@PathVariable Long taskId,
                              @RequestParam Long workerId,
                              @RequestParam(defaultValue = "1") Integer workerTypeCode) {
        String cityCode = TenantZoneContext.get().getCityCode();
        String stockKey = "lifebazaar:task:stock:" + cityCode + ":" + taskId;
        String winnerKey = "lifebazaar:task:winner:" + cityCode + ":" + taskId;
        String winnerIdentity = workerTypeCode + "_" + workerId;

        Long res = redisTemplate.execute(grabTaskScript, Arrays.asList(stockKey, winnerKey), winnerIdentity, "86400");
        if (res != null && res == 1L) {
            return R.ok("抢单成功！接单主体：" + winnerIdentity + "，已为您彠往同城泟通窗口");
        }
        return R.fail("手慢一步，该任务已被其他邻里接走啦");
    }
}
