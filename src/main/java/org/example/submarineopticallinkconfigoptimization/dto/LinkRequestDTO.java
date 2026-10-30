package org.example.submarineopticallinkconfigoptimization.dto;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkRequestDTO {

    // Link name
    private StringProperty linkName = new SimpleStringProperty("");

    // Link description
    private StringProperty linkDescription = new SimpleStringProperty("");

    // Length of the link (km)
    private DoubleProperty linkLength =  new SimpleDoubleProperty(0.0);

    // Optical fiber attenuation (dB/km)
    private DoubleProperty linkAttenuation =  new SimpleDoubleProperty(0.0);

    // Transmitter output power (dBm)
    private DoubleProperty transmitterOutputPower =  new SimpleDoubleProperty(0.0);

    // Minimum required received optical power (dBm)
    private DoubleProperty minReceivedPower =  new SimpleDoubleProperty(0.0);

    // Minimum permissible gain per amplifier (dB)
    private DoubleProperty minAmplifierGain =  new SimpleDoubleProperty(0.0);

    // Maximum permissible gain per amplifier (dB)
    private DoubleProperty maxAmplifierGain =  new SimpleDoubleProperty(0.0);

    // Minimum input power for each amplifier (dBm)
    private DoubleProperty minAmplifierInputPower =  new SimpleDoubleProperty(0.0);

    // Maximum output power of the amplifiers (dBm)
    private DoubleProperty maxAmplifierOutputPower =  new SimpleDoubleProperty(0.0);

    // Maximum number of amplifiers
    private IntegerProperty maxNumberOfAmplifiers = new SimpleIntegerProperty(0);



}
