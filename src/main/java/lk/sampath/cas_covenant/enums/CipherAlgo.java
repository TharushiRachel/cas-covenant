package lk.sampath.cas_covenant.enums;

public enum CipherAlgo {
  RSA("RSA"),
  AES("AES"),
  DES("DES"),
  TRIPLE_DES("3DES");
  private final String cipherAlgorithm;

  CipherAlgo(String algo) {
    this.cipherAlgorithm = algo;
  }

  public String getAlgorithm() {
    return this.cipherAlgorithm;
  }
}
