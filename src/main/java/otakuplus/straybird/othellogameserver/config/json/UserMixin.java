package otakuplus.straybird.othellogameserver.config.json;

import com.fasterxml.jackson.annotation.JsonView;

public abstract class UserMixin {
    @JsonView(UserView.WebUser.class)
    public abstract Integer getUserId();

    @JsonView(UserView.WebUser.class)
    public abstract String getUsername();

    @JsonView(UserView.WebUser.class)
    public abstract String getEmailAddress();
}
