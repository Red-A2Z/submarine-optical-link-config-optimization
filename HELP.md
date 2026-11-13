# Table of contents:
### 1. Overview
### 2. Input
### 3. Output
### 4. Modeling assumptions
### 5. Variable Constraints
### 6. Functionalities
### 7. How-to guide
### 8. Main technical stack
### 9. Database table script

<br>

## 1. Overview:
 This is an application that can help in designing submarine optical links by calculating the minimum required number of amplifiers to use as well as the maximum distance between two successive ones.
 
<br>

## 2. Input:
- Link name
- Link description
- Length of the link (km)
- Optical fiber attenuation (dB/km)
- Transmitter output power (dBm)
- Minimum required received optical power (dBm)
- Minimum permissible gain per amplifier (dB)
- Maximum permissible gain per amplifier (dB)
- Minimum input power for each amplifier (dBm)
- Maximum output power of each amplifier (dBm)
- Maximum allowed number of amplifiers

<br>

## 3. Output:
- The optimal (minimum) number of amplifiers
- The optimal (maximum) distance between two successive amplifiers (km)
- The individual gain of each amplifier (dB)
- The input power of each amplifier
- The final received power

## 4. Modeling assumptions:
In order design a simple yet effective model, we considered the following assumptions:
- The distance between amplifiers is the same.
- All the amplifiers contribute with the same gain, which is equal to the loss on the distance between successive amplifiers, and which is fixed during the design of the link.
- **The loss on a portion of the link** is equal to the **Optical fiber attenuation (dB/km)** multiplied by **the length of that portion**
- A strict inequality is approximated by adding 0.000001 to the lower bound





