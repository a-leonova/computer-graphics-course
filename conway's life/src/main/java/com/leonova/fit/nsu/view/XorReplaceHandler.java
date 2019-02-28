package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.GameController;

import javax.swing.*;

public class XorReplaceHandler {

    private JToggleButton xorButton;
    private JToggleButton replaceButton;
    private JRadioButtonMenuItem xorMI;
    private JRadioButtonMenuItem replaceMI;


    public XorReplaceHandler(JToggleButton xorButton, JToggleButton replaceButton, JRadioButtonMenuItem xorMI, JRadioButtonMenuItem replaceMI) {
        this.xorButton = xorButton;
        this.replaceButton = replaceButton;
        this.xorMI = xorMI;
        this.replaceMI = replaceMI;
    }

    public void xorPressed(GameController gameController){
            gameController.setXor();
            xorMI.setSelected(true);
            xorButton.setSelected(true);
    }

    public void replacePressed(GameController gameController){
            gameController.setReplace();
            replaceMI.setSelected(true);
            replaceButton.setSelected(true);
    }
}
