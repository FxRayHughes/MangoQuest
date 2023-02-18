package me.Cutiemango.MangoQuest.model;

import me.Cutiemango.MangoQuest.I18n;

public enum RedoSetting {
    DAILY(I18n.locMsg("QuestEditor.RedoSetting.Daily")),
    WEEKLY(I18n.locMsg("QuestEditor.RedoSetting.Weekly")),
    COOLDOWN(I18n.locMsg("QuestEditor.RedoSetting.Cooldown")),
    ONCE_ONLY(I18n.locMsg("QuestEditor.RedoSetting.OnceOnly"));

    RedoSetting(String s) {
        name = s;
    }

    private final String name;

    public String getName() {
        return name;
    }
}