package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Avatar {
    VolunteerAvatar("volunteer-default.png"),
    OrganizerAvatar("organizer-default.png"),
    RegulatorAvatar("regulator-default.png");

    private final String AvatarName;
}
