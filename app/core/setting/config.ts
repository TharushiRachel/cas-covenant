export class Config {
  public static CAS_LEAD_CONTEXT_PATH = "";
  public static readonly LEAD_CONTEXT_PATH = "lead";
  public static readonly AA_CONTEXT_PATH = "advance-analytics";
  public static readonly COVENANT_CONTEXT_PATH = "/cas-covenant/api";

  public static getLeadContextPath(): string {
    return `/cas/api/microservice/${Config.LEAD_CONTEXT_PATH}`;
  }

  public static getAAContextPath(): string {
    return `/cas/api/microservice/${Config.AA_CONTEXT_PATH}`;
  }

  public static getCovenantContextPath(): string {
    return Config.COVENANT_CONTEXT_PATH;
  }
}
