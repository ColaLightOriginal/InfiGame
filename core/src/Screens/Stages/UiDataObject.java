package Screens.Stages;

import com.puputan.infi.Objects.Player.PowerUpsEnum;
import lombok.Getter;

@Getter
public class UiDataObject {
    private PowerUpsEnum powerUpsEnum;
    private String type;
    private String tag;

    public UiDataObject(PowerUpsEnum powerUpsEnum, String type, String tag){
        this.powerUpsEnum = powerUpsEnum;
        this.type = type;
        this.tag = tag;
    }
}
