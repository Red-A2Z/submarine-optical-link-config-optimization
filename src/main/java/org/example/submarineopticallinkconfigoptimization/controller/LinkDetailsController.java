package org.example.submarineopticallinkconfigoptimization.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;

public class LinkDetailsController {

    @FXML
    VBox inputVbox;
    @FXML
    VBox outputVbox;

    private final LinkResponseDTO linkResponseDTO;


    public LinkDetailsController(LinkResponseDTO linkResponseDTO){
        this.linkResponseDTO = linkResponseDTO;


    }

    @FXML
    public void initialize(){

        inputVbox.getChildren().add(new FormRenderer(staticInputFormBuilder()));
        outputVbox.getChildren().add(new FormRenderer(staticOutputFormBuilder()));

    }




    private Form staticInputFormBuilder(){

        Field<StringField> staticInputField_1 = Field.ofStringType(linkResponseDTO.getLinkName().get())
                .label("Link name:")
                .editable(false);

        Field<StringField> staticInputField_2 = Field.ofStringType(linkResponseDTO.getLinkDescription().get())
                .label("Link description:")
                .editable(false);

        Field<DoubleField> staticInputField_3 = Field.ofDoubleType(linkResponseDTO.getLinkLength())
                .label("Length of the link (km):")
                .editable(false);

        Field<DoubleField> staticInputField_4 = Field.ofDoubleType(linkResponseDTO.getLinkAttenuation())
                .label("Optical fiber attenuation (dB/km):")
                .editable(false);

        Field<DoubleField> staticInputField_5 = Field.ofDoubleType(linkResponseDTO.getTransmitterOutputPower())
                .label("Transmitter output power (dBm):")
                .editable(false);

        Field<DoubleField> staticInputField_6 = Field.ofDoubleType(linkResponseDTO.getMinReceivedPower())
                .label("Min required received optical power (dBm):")
                .editable(false);

        Field<DoubleField> staticInputField_7 = Field.ofDoubleType(linkResponseDTO.getMinAmplifierGain())
                .label("Min permissible gain per amplifier (dB):")
                .editable(false);

        Field<DoubleField> staticInputField_8 = Field.ofDoubleType(linkResponseDTO.getMaxAmplifierGain())
                .label("Max permissible gain per amplifier (dB):")
                .editable(false);

        Field<DoubleField> staticInputField_9 = Field.ofDoubleType(linkResponseDTO.getMinAmplifierInputPower())
                .label("Min input power for each amplifier (dBm):")
                .editable(false);

        Field<DoubleField> staticInputField_10 = Field.ofDoubleType(linkResponseDTO.getMaxAmplifierOutputPower())
                .label("Max output power of each amplifier (dBm):")
                .editable(false);

        Field<IntegerField> staticInputField_11 = Field.ofIntegerType(linkResponseDTO.getMaxNumberOfAmplifiers())
                .label("Max number of amplifiers:")
                .editable(false);



        return Form.of(
                Group.of(
                        staticInputField_1,
                        staticInputField_2,
                        staticInputField_3,
                        staticInputField_4,
                        staticInputField_5,
                        staticInputField_6,
                        staticInputField_7,
                        staticInputField_8,
                        staticInputField_9,
                        staticInputField_10,
                        staticInputField_11
                ));
    }





    private Form staticOutputFormBuilder(){

        Field<IntegerField> staticOutputField_1 = Field.ofIntegerType(linkResponseDTO.getNumberOfAmplifiers())
                .label("The optimal number of amplifiers:")
                .editable(false);

        Field<DoubleField> staticOutputField_2 = Field.ofDoubleType(linkResponseDTO.getDistanceBetweenAmplifiers())
                .label("The distance between two successive amplifiers (km):")
                .editable(false);

        Field<DoubleField> staticOutputField_3 = Field.ofDoubleType(linkResponseDTO.getAmplifierGain())
                .label("The individual gain of each amplifier (dB):")
                .editable(false);

        Field<DoubleField> staticOutputField_4 = Field.ofDoubleType(linkResponseDTO.getAmplifierInputPower())
                .label("The input power of each amplifier:")
                .editable(false);

        Field<DoubleField> staticOutputField_5 = Field.ofDoubleType(linkResponseDTO.getFinalReceivedPower())
                .label("The final received power:")
                .editable(false);


        return Form.of(
                Group.of(
                        staticOutputField_1,
                        staticOutputField_2,
                        staticOutputField_3,
                        staticOutputField_4,
                        staticOutputField_5
                ));
    }



}
