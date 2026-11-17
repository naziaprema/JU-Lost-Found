package lost.found;

/** The signed-in account exists only for the running application. */
public final class Session {
    private static volatile AccountStore.Account current;
    private Session() {}
    public static AccountStore.Account current() { return current; }
    public static void signIn(AccountStore.Account account) { current = java.util.Objects.requireNonNull(account); }
    public static void signOut() { current = null; }
}
