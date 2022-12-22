package com.universty.anas.breacktimer;

import java.awt.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public enum Fonts {
    MAIN_FONT(new Font("Arial", Font.PLAIN, 16)),
    Link_FONT(new Font("Arial", Font.PLAIN, 16)),
    INPUT_FIELD_FONT(new Font("Arial", Font.PLAIN, 16)),
    SP_BUTTON_FONT(new Font("Arial", Font.PLAIN, 16)),
    ;

    private final Font font;

    Fonts(final Font font) {
        this.font = font;
    }

    public Font font() {
        return font;
    }

    public void applyToAll(final Component... components) {
        for (final var component : components) {
            component.setFont(font);
        }
    }
}
