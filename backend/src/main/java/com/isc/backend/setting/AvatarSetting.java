package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AvatarSetting {
    VolunteerAvatar("volunteer-default.png"),
    OrganizerAvatar("organizer-default.png"),
    RegulatorAvatar("regulator-default.png");

    private final String AvatarName;
}
