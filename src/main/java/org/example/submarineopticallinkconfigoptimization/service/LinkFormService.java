package org.example.submarineopticallinkconfigoptimization.service;

import org.example.submarineopticallinkconfigoptimization.dto.LinkRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class LinkFormService {




    public List<String> verifyInput(LinkRequestDTO linkRequestDTO){

        List<String> listOfMessages = new ArrayList<>();

        if(!linkRequestDTO.getLinkName().get().replaceAll("\\s","").matches(".+")){
            listOfMessages.add("Link name must be at least one non-whitespace character long.");
        }

        if(!linkRequestDTO.getLinkDescription().get().replaceAll("\\s","").matches(".+")){
            listOfMessages.add("Link description must be at least one non-whitespace character long.");
        }

        if(!(linkRequestDTO.getLinkLength().get()>0)){
            listOfMessages.add("Length of the link must be greater than 0.");
        }

        if(!(linkRequestDTO.getLinkAttenuation().get()>0)){
            listOfMessages.add("Optical fiber attenuation must be greater than 0.");
        }

        if(!(linkRequestDTO.getMaxNumberOfAmplifiers().get()>0)){
            listOfMessages.add("Maximum number of amplifiers must be greater than 0.");
        }

        if(!(linkRequestDTO.getMaxAmplifierGain().get()>linkRequestDTO.getMinAmplifierGain().get())){
            listOfMessages.add("Maximum permissible gain per amplifier must be greater than Minimum permissible gain per amplifier.");
        }

        if(!(linkRequestDTO.getTransmitterOutputPower().get()>=linkRequestDTO.getMinReceivedPower().get())){
            listOfMessages.add("Transmitter output power must be equal or greater than Minimum required received optical power.");
        }

        if(!(linkRequestDTO.getMaxAmplifierOutputPower().get()>=linkRequestDTO.getTransmitterOutputPower().get())){
            listOfMessages.add("Maximum output power of the amplifiers must be equal or greater than Transmitter output power.");
        }

        if(!(linkRequestDTO.getTransmitterOutputPower().get()>=linkRequestDTO.getMinAmplifierInputPower().get())){
            listOfMessages.add("Transmitter output power must be equal or greater than Minimum input power for each amplifier.");
        }


        return listOfMessages;
    }


}
