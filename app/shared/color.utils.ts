export class ColorUtils {

  public static getColorCodeFromString(str: string): string {
    return ColorUtils.getHexColorCode(ColorUtils.hashCode(str));
  }

  private static hashCode(str): number {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    return hash;
  }

  private static getHexColorCode(hashCode: number): string {
    let c = (hashCode & 0x00FFFFFF)
      .toString(16)
      .toUpperCase();

    return '#' + ("00000".substring(0, 6 - c.length) + c);
  }

}
