package org.example.submarineopticallinkconfigoptimization.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Link {

    /*

    Fields used as input

     */

    // Link name
    private String linkName;

    // Link description
    private String linkDescription;

    // Length of the link (km)
    private double linkLength;

    // Optical fiber attenuation (dB/km)
    private double linkAttenuation;

    // Transmitter output power (dBm)
    private double transmitterOutputPower;

    // Minimum required received optical power (dBm)
    private double minReceivedPower;

    // Minimum permissible gain per amplifier (dB)
    private double minAmplifierGain;

    // Maximum permissible gain per amplifier (dB)
    private double maxAmplifierGain;

    // Minimum input power for each amplifier (dBm)
    private double minAmplifierInputPower;

    // Maximum output power of each amplifier (dBm)
    private double maxAmplifierOutputPower;

    // Maximum number of amplifiers
    private double maxNumberOfAmplifiers;



    /*

     Calculations output

    */

    // The optimal number of amplifiers
    private int numberOfAmplifiers;

    // The distance between two successive amplifiers (km)
    private double distanceBetweenAmplifiers;

    //The individual gain of each amplifier (dB)
    private double amplifierGain;

    // The input power of each amplifier
    private double amplifierInputPower;

    // The final received power
    private double finalReceivedPower;


}
