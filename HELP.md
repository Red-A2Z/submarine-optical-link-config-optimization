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

## 3. Output:
- Optimal (minimum) number of amplifiers
- Optimal (maximum) distance between two successive amplifiers (km)
- Individual gain of each amplifier (dB)
- Input power of each amplifier
- Final received power

## 4. Modeling assumptions:
In order design a simple yet effective model, we considered the following assumptions:
- The distance between amplifiers is the same.
- All the amplifiers contribute with the same gain, which is equal to the loss on the distance between successive amplifiers, and which is fixed during the design of the link.
- **The loss on a portion of the link** is equal to the **Optical fiber attenuation (dB/km)** multiplied by **the length of that portion**
- A strict inequality is approximated by adding 0.000001 to the lower bound

## 5. Variable Constraints:
- The optical power at the input of each amplifier must be greater than or equal to the **Minimum input power for each amplifier**.
- The number of amplifiers to use must be less than or equal to the **Maximum allowed number of amplifiers**.
- The gain that the amplifier has to provide (**Individual gain of each amplifier**) must be between the **Minimum permissible gain per amplifier** and the **Maximum permissible gain per amplifier**.
- The **Final received power** must be greater than or equal to the **Minimum required received optical power**.

## 6. Functionalities
- Input validation
- Calculation
- In the case of successful calculations, link record creation in the database
- List of created links display
- Link name and/or description update
- Link record removal from the database
- Link information (input + calculation result) export as PDF

## 7. How-to guide
To use this app:
- Replace the value of the url field inside **LinkRepository.java** with the link of your PostgreSQL database. 
- Create a table inside that database using the script in the section **"9. Database table script"**.
- Run **Launcher.java** from your IDE.


## 8. Main technical stack
- Java, JavaFX (including JavaFX's CSS) & FXML
- Formsfx
- Lombok
- OjAlgo
- Gurobi (Not tested: required a license. The current app logic uses OjAlgo instead)
- PostgreSQL & JDBC
- CommonMark
- HTML, CSS & Openhtmltopdf
- Maven


## 9. Database table script

create table link (<br>
id BIGINT generated always as identity primary key,<br>
linkName TEXT not null,<br>
linkDescription TEXT not null,<br>
linkLength FLOAT8 not null,<br>
linkAttenuation FLOAT8 not null,<br>
transmitterOutputPower FLOAT8 not null,<br>
minReceivedPower FLOAT8 not null,<br>
minAmplifierGain FLOAT8 not null,<br>
maxAmplifierGain FLOAT8 not null,<br>
minAmplifierInputPower FLOAT8 not null,<br>
maxAmplifierOutputPower FLOAT8 not null,<br>
maxNumberOfAmplifiers INTEGER not null,<br>
numberOfAmplifiers INTEGER not null,<br>
distanceBetweenAmplifiers FLOAT8 not null,<br>
amplifierGain FLOAT8 not null,<br>
amplifierInputPower FLOAT8 not null,<br>
finalReceivedPower FLOAT8 not null<br>
);




