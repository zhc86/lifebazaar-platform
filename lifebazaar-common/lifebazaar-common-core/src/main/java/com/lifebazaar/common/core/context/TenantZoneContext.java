package com.lifebazaar.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TenantZoneContext {
    private static final TransmittableThreadLocal<ZoneInfo> CONTEXT = new TransmittableThreadLocal<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZoneInfo {
        private String regionCode;
        private String countryCode;
        private String cityCode;
        private String langCode;
        private Boolean elderMode;
        private Long userId;
    }

    public static void set(ZoneInfo zoneInfo) { CONTEXT.set(zoneInfo); }

    public static ZoneInfo get() {
        ZoneInfo info = CONTEXT.get();
        return info != null ? info : ZoneInfo.builder()
                .regionCode("CN-NORTH").countryCode("CN").cityCode("010")
                .langCode("zh-CN").elderMode(false).build();
    }

    public static void clear() { CONTEXT.remove(); }
}
