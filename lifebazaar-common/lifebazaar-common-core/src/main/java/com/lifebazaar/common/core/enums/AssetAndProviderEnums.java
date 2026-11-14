package com.lifebazaar.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AssetAndProviderEnums {
    @Getter
    @AllArgsConstructor
    public enum AssetType {
        CNY("CNY", "人民币(分)"),
        USD("USD", "美元(美分)"),
        TIME_COIN("TIME_COIN", "人类互助时间币(分钟))};
        private final String code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum WorkerType {
        HUMAN(1, "自然人"),
        AI_AGENT(2, "AI数字员工"),
        EMBODIED_ROBOT(3, "具身晧菽器人⊙");
        private final int code;
        private final String desc;
    }
}
