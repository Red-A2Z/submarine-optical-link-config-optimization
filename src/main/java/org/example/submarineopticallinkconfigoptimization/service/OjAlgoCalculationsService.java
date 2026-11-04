package org.example.submarineopticallinkconfigoptimization.service;

import org.example.submarineopticallinkconfigoptimization.dto.LinkRequestDTO;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.entity.Link;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

public class OjAlgoCalculationsService {


    private final LinkService linkService = new LinkService();


    public LinkResponseDTO calculate(LinkRequestDTO linkRequestDTO){

        double length = linkRequestDTO.getLinkLength().get();
        double attenuation = linkRequestDTO.getLinkAttenuation().get();
        double transmitterOutputPower = linkRequestDTO.getTransmitterOutputPower().get();
        double minAmplifierInputPower = linkRequestDTO.getMinAmplifierInputPower().get();
        int maxNumberOfAmplifiers = linkRequestDTO.getMaxNumberOfAmplifiers().get();
        double maxAmplifierGain = linkRequestDTO.getMaxAmplifierGain().get();
        double minAmplifierGain = linkRequestDTO.getMinAmplifierGain().get();
        double minReceivedPower = linkRequestDTO.getMinReceivedPower().get();



        //  Input Power at Amplifier >= minAmplifierInputPower
        // numberOfAMplifiers = (int) length/distanceBetweenAmplifiers
        int minAllowedNumberOfAmplifiers = (int) (attenuation*length/(transmitterOutputPower-minAmplifierInputPower));


        for (int n=minAllowedNumberOfAmplifiers; n<=maxNumberOfAmplifiers; n++){

            ExpressionsBasedModel model =  new ExpressionsBasedModel();

            // "d" is the distance between two successive amplifiers
            Variable d = model.addVariable().lower(length/(n+1)+0.000001).upper(length/n);  // 0.000001 to approximate the strict inequality

            // the amplifier must be able to compensate the loss
            model.addExpression("c1")
                    .set(d,attenuation).lower(minAmplifierGain).upper(maxAmplifierGain);

            //  Received power >= minReceivedPower
            model.addExpression("c2")
                    .set(d,-attenuation*n).upper(transmitterOutputPower-minReceivedPower-attenuation*length);


            // We want to maximize d
            model.addExpression("objective")
                    .set(d,1).weight(1);

            Optimisation.Result result = model.maximise();

            if(result.getState().isFeasible()){

                double maxDistanceBetweenAmplifiers = result.doubleValue(0);

                double amplifierGain = maxDistanceBetweenAmplifiers*attenuation;
                double amplifierInputPower = transmitterOutputPower - maxDistanceBetweenAmplifiers*attenuation;
                double finalReceivedPower = transmitterOutputPower - attenuation*length + n*attenuation*maxDistanceBetweenAmplifiers;

                Link link = linkService.mapToEntity(linkRequestDTO,maxDistanceBetweenAmplifiers, n, amplifierGain,amplifierInputPower, finalReceivedPower);

                // implement DB logic //


                return linkService.mapToResponseDto(link);


            }

        }


        // No solution found
        System.out.println("No solution found");
        return null;


    }




}
