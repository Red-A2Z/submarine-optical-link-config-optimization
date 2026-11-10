package org.example.submarineopticallinkconfigoptimization.dto;

import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkUpdateRequestDTO {

    private long id;
    private StringProperty linkName;
    private StringProperty linkDescription;
}
