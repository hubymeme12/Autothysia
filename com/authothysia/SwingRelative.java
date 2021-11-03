package com.authothysia;

import javax.swing.*;
import java.awt.Point;

public class SwingRelative {
    JComponent Current;
    JComponent Relative;

    // Calling this will automatically make changes on the object
    SwingRelative(JComponent _current, JComponent _relative, int _x, int _y) {
        Current = _current;
        Relative = _relative;

        // append offset
        makeOffset(_x, _y);
    }

    // Calling this simply sets the values of the JComponents
    SwingRelative(JComponent _current, JComponent _relative) {
        Current = _current;
        Relative = _relative;
    }

    // Manually assign an offset for x and y axes of the object
    public void makeOffset(int _x, int _y) {
        Point pair = Relative.getLocation();
        Current.setLocation(pair.x + _x, Relative.getHeight() + pair.y + _y);
    }
}
