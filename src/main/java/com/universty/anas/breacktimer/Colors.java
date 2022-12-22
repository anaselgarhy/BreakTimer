package com.universty.anas.breacktimer;

import java.awt.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/12/2022
 */
public enum Colors {
    LINK_COLOR("#0000FF"),
    BACKGROUND_COLOR("#a93020"),
    INPUT_FIELD_COLOR("#b4b4b4"),
    INPUT_FIELD_TEXT_COLOR("#000000"),
    INPUT_FIELD_BORDER_COLOR("#2e7682"),
    TEXT_COLOR("#FFFFFF"),
    SP_BUTTON_COLOR("#2e7682"),
    SP_BUTTON_TEXT_COLOR("#FFFFFF")
    ;

    private final String color;

    Colors(final String color) {
        this.color = color;
    }

    /**
     * Get the actual color
     * @return The color
     */
    public Color color() {
        return Color.decode(color);
    }

    /**
     * Apply the color to all the components as the background color
     * @param components The components to apply the color to
     */
    public void applyToAllAsBackground(final Component... components) {
        for (final var component : components) {
            component.setBackground(color());
        }
    }

    /**
     * Apply the color to all the components as the foreground color
     * @param components The components to apply the color to
     */
    public void applyToAllAsForeground(final Component... components) {
        for (final var component : components) {
            component.setForeground(color());
        }
    }
}
