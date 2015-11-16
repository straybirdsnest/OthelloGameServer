package otakuplus.straybird.othellogameserver.config.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import otakuplus.straybird.othellogameserver.models.User;

public class OthelloGameServerDataJacksonModule extends SimpleModule {

    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 0;
    public static final int PATCH_VERSION = 1;

    public OthelloGameServerDataJacksonModule() {
        super("BlackServerData", new Version(MAJOR_VERSION, MINOR_VERSION, PATCH_VERSION, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(User.class, UserMixin.class);
    }

}
