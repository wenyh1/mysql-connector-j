## MYSQL 支持GMSSL

### 打包方式
见 [README3.md](README3.md)

### 取包地址：
在本仓库的GMsslRelease目录下
- mysql-connector-java-5.1.49-SNAPSHOT.jar  不包含gmssl_privoder.jar（使用时需要在自己的项目中导入gmssl_privoder.jar）
- mysql-connector-java-5.1.49-SNAPSHOT-gmssl.jar  包含gmssl_privoder.jar

### 配置如下
```
final String DB_URL = "jdbc:mysql://localhost:8066/testdb?" +
                // "requireSSL=true" +
                "&useCompression=true" + // 进行压缩
                "&useSSL=true" +
                "&useGMSSL=true" +
                "&verifyServerCertificate=true" +
                "&enabledSSLCipherSuites=ECC_SM4_CBC_SM3" +
                "&trustRootCertificateKeyStoreUrl=file:/Users/wd/Documents/Action/project/gmssl/gmssl-test/src/main/java/com/gmssl/skeystore/sm2.rca.pem" +
                "&trustMiddleCertificateKeyStoreUrl=file:/Users/wd/Documents/Action/project/gmssl/gmssl-test/src/main/java/com/gmssl/skeystore/sm2.oca.pem" +
                "&clientCertificateKeyStoreType=PKCS12" +
                "&clientCertificateKeyStoreUrl=file:/Users/wd/Documents/Action/project/gmssl/gmssl-test/src/main/java/com/gmssl/ckeystore/sm2.action.both.pfx" +
                "&clientCertificateKeyStorePassword=12345678" +
                "&clientCertificateKeyStoreType=PKCS12";
```
### 参数说明：
- useSSL：是否开启表示开启ssl，默认为false；
- useGMSSL：是否使用国密ssl；使用出现问题，默认为false；
- verifyServerCertificate：jdbc是否验证服务端的身份，默认为false；
- enabledSSLCipherSuites：设置加密套件；useGMSSL为true时，支持的加密条套件目前仅支持两个，分别：ECC_SM4_CBC_SM3,ECDHE_SM4_CBC_SM3
- trustRootCertificateKeyStoreUrl：根ca证书；取自[国密实验室](https://www.gmssl.cn/gmssl/index.jsp?go=gmsdk)
- trustMiddleCertificateKeyStoreUrl：二级ca证书；取[国密实验室](https://www.gmssl.cn/gmssl/index.jsp?go=gmsdk)
- trustCertificateKeyStoreType：识别ca证书类型，useGMSSL为true 默认为PKCS12，useGMSSL为false,默认为JKS
- clientCertificateKeyStoreUrl：客户端的双(签名+加密)证书; 取自[国密实验室](https://www.gmssl.cn/gmssl/index.jsp?go=gmsdk)
- clientCertificateKeyStorePassword：客户端双证书对应密码
- clientCertificateKeyStoreType：识别客户算证书类型，useGMSSL为true 默认为PKCS12，useGMSSL为false,默认为JKS

