package codelook.jpa.queries;

import codelook.jpa.model.UserInfo;

public class GetOrdersQuery {
    private UserInfo user;

    public GetOrdersQuery(UserInfo user) {
        this.user = user;
    }

    public UserInfo getUser() {
        return user;
    }
}
