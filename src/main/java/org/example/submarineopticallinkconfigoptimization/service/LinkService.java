package org.example.submarineopticallinkconfigoptimization.service;

import javafx.beans.property.SimpleStringProperty;
import org.example.submarineopticallinkconfigoptimization.dto.LinkRequestDTO;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.dto.LinkUpdateRequestDTO;
import org.example.submarineopticallinkconfigoptimization.entity.Link;
import org.example.submarineopticallinkconfigoptimization.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;

public class LinkService {

    LinkRepository linkRepository = new LinkRepository();




    public List<String> verifyFormInput(LinkRequestDTO linkRequestDTO){

        List<String> listOfMessages = new ArrayList<>();

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

        if(!(linkRequestDTO.getTransmitterOutputPower().get()>linkRequestDTO.getMinReceivedPower().get())){
            listOfMessages.add("Transmitter output power must be greater than Minimum required received optical power.");
        }

        if(!(linkRequestDTO.getMaxAmplifierOutputPower().get()>=linkRequestDTO.getTransmitterOutputPower().get())){
            listOfMessages.add("Maximum output power of the amplifiers must be equal or greater than Transmitter output power.");
        }

        if(!(linkRequestDTO.getTransmitterOutputPower().get()>=linkRequestDTO.getMinAmplifierInputPower().get())){
            listOfMessages.add("Transmitter output power must be equal or greater than Minimum input power for each amplifier.");
        }


        return listOfMessages;
    }







    public Link mapToEntity(LinkRequestDTO linkRequestDTO ,
                             double maxDistanceBetweenAmplifiers, int minNumberOfAmplifiers,
                             double amplifierGain, double amplifierInputPower, double finalReceivedPower){

        Link link  = new Link();

        //Input fields
        link.setLinkName(linkRequestDTO.getLinkName().get());
        link.setLinkDescription(linkRequestDTO.getLinkDescription().get());
        link.setLinkLength(linkRequestDTO.getLinkLength().get());
        link.setLinkAttenuation(linkRequestDTO.getLinkAttenuation().get());
        link.setTransmitterOutputPower(linkRequestDTO.getTransmitterOutputPower().get());
        link.setMinReceivedPower(linkRequestDTO.getMinReceivedPower().get());
        link.setMinAmplifierGain(linkRequestDTO.getMinAmplifierGain().get());
        link.setMaxAmplifierGain(linkRequestDTO.getMaxAmplifierGain().get());
        link.setMinAmplifierInputPower(linkRequestDTO.getMinAmplifierInputPower().get());
        link.setMaxAmplifierOutputPower(linkRequestDTO.getMaxAmplifierOutputPower().get());
        link.setMaxNumberOfAmplifiers(linkRequestDTO.getMaxNumberOfAmplifiers().get());

        //Output fields
        link.setDistanceBetweenAmplifiers(maxDistanceBetweenAmplifiers);
        link.setNumberOfAmplifiers(minNumberOfAmplifiers);
        link.setAmplifierGain(amplifierGain);
        link.setAmplifierInputPower(amplifierInputPower);
        link.setFinalReceivedPower(finalReceivedPower);


        return link;

    }


    public LinkResponseDTO mapToResponseDto(Link link){

        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();

        linkResponseDTO.setId(link.getId());

        //Input fields
        linkResponseDTO.setLinkName(new SimpleStringProperty(link.getLinkName()));
        linkResponseDTO.setLinkDescription(new SimpleStringProperty(link.getLinkDescription()));
        linkResponseDTO.setLinkLength(link.getLinkLength());
        linkResponseDTO.setLinkAttenuation(link.getLinkAttenuation());
        linkResponseDTO.setTransmitterOutputPower(link.getTransmitterOutputPower());
        linkResponseDTO.setMinReceivedPower(link.getMinReceivedPower());
        linkResponseDTO.setMinAmplifierGain(link.getMinAmplifierGain());
        linkResponseDTO.setMaxAmplifierGain(link.getMaxAmplifierGain());
        linkResponseDTO.setMinAmplifierInputPower(link.getMinAmplifierInputPower());
        linkResponseDTO.setMaxAmplifierOutputPower(link.getMaxAmplifierOutputPower());
        linkResponseDTO.setMaxNumberOfAmplifiers(link.getMaxNumberOfAmplifiers());

        //Output fields
        linkResponseDTO.setDistanceBetweenAmplifiers(link.getDistanceBetweenAmplifiers());
        linkResponseDTO.setNumberOfAmplifiers(link.getNumberOfAmplifiers());
        linkResponseDTO.setAmplifierGain(link.getAmplifierGain());
        linkResponseDTO.setAmplifierInputPower(link.getAmplifierInputPower());
        linkResponseDTO.setFinalReceivedPower(link.getFinalReceivedPower());

        return linkResponseDTO;

    }




    public List<LinkResponseDTO> getAllRecords(){

        List<LinkResponseDTO> recordsListResponseDTO = new ArrayList<>();
        List<Link> records = linkRepository.getAllRecords();

        for(Link record: records){
            recordsListResponseDTO.add(mapToResponseDto(record));
        }

        return recordsListResponseDTO;


    }




    public void updateLinkNameDescription(LinkUpdateRequestDTO linkUpdateRequestDTO,boolean shouldUpdateName, boolean shouldUpdateDescription){


        if(!shouldUpdateName && !shouldUpdateDescription){
            System.out.println("No update required");
        } else if (!shouldUpdateName) {
            linkRepository.updateLinkNameDescription(linkUpdateRequestDTO.getId(),null,linkUpdateRequestDTO.getLinkDescription().get());
        } else if (!shouldUpdateDescription) {
            linkRepository.updateLinkNameDescription(linkUpdateRequestDTO.getId(),linkUpdateRequestDTO.getLinkName().get(),null);
        }else {
            linkRepository.updateLinkNameDescription(linkUpdateRequestDTO.getId(),linkUpdateRequestDTO.getLinkName().get(),linkUpdateRequestDTO.getLinkDescription().get());
        }
    }


    public void deleteLink(long linkId){

        linkRepository.deleteLink(linkId);

    }


}
