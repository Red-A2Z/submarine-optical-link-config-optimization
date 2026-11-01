package org.example.submarineopticallinkconfigoptimization.service;

import com.gurobi.gurobi.*;
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




    public double[] calculateNumberOfAmplifiers(LinkRequestDTO linkRequestDTO) throws GRBException {

        double length = linkRequestDTO.getLinkLength().get();
        double attenuation = linkRequestDTO.getLinkAttenuation().get();
        double transmitterOutputPower = linkRequestDTO.getTransmitterOutputPower().get();
        double minAmplifierInputPower = linkRequestDTO.getMinAmplifierInputPower().get();
        int maxNumberOfAmplifiers = linkRequestDTO.getMaxNumberOfAmplifiers().get();
        double maxAmplifierGain = linkRequestDTO.getMaxAmplifierGain().get();
        double minAmplifierGain = linkRequestDTO.getMinAmplifierGain().get();
        double minReceivedPower = linkRequestDTO.getMinReceivedPower().get();


        GRBEnv env  =  new GRBEnv("maxDistanceBetweenAmplifiers.log");
        GRBModel model = new GRBModel(env);


        GRBVar d  = model.addVar(0,(transmitterOutputPower-minAmplifierInputPower)/attenuation,1, GRB.CONTINUOUS,"distanceBetweenAmplifiers");

        GRBVar n  = model.addVar(0,maxNumberOfAmplifiers,0, GRB.INTEGER,"numberOfAmplifiers");

        // n = (int) length/d
        GRBQuadExpr grbQuadExpr1 = new GRBQuadExpr();
        grbQuadExpr1.addTerm(1,d,n);
        model.addQConstr(grbQuadExpr1,GRB.LESS_EQUAL,length,"c1");
        GRBQuadExpr grbQuadExpr2 = new GRBQuadExpr();
        grbQuadExpr2.addTerm(1,d,n);
        grbQuadExpr2.addTerm(1,d);
        grbQuadExpr2.addConstant(0.999999);// we use 0.999999 to approximate the strict inequality
        model.addQConstr(length,GRB.LESS_EQUAL,grbQuadExpr2,"c2");


        //  Input Power at Amplifier >= minAmplifierInputPower
        model.addConstr(d,GRB.LESS_EQUAL,(transmitterOutputPower-minAmplifierInputPower)/attenuation,"c3");

        // the amplifier must be able to compensate the loss
        model.addConstr(d,GRB.LESS_EQUAL,maxAmplifierGain/attenuation,"c4");
        model.addConstr(minAmplifierGain/attenuation,GRB.LESS_EQUAL,d,"c5");

        //  Received power >= minReceivedPower
        GRBQuadExpr grbQuadExpr3 = new GRBQuadExpr();
        grbQuadExpr3.addConstant(transmitterOutputPower-attenuation*length);
        grbQuadExpr3.addTerm(attenuation,n,d);
        model.addQConstr(minReceivedPower,GRB.LESS_EQUAL,grbQuadExpr3,"c6");

        // Maximize d
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1,d);
        model.setObjective(expr,GRB.MAXIMIZE);

        // Perform calculations
        model.optimize();

        //At least one solution was found
        if(model.get(GRB.IntAttr.SolCount)>0){
            double maxDistanceBetweenAmplifiers = d.get(GRB.DoubleAttr.X);
            int minNumberOfAmplifiers = (int) n.get(GRB.DoubleAttr.X);
            return new double[]{maxDistanceBetweenAmplifiers,minNumberOfAmplifiers};
        }

        // No solution found
        System.out.println("No solution found");
        return new double[]{-1,-1};

    }




}
