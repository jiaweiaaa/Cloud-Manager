package com.example.server.config.security;
/**
 * JwtToken工具类
 *
 * @author Jiawei
 * @since 1.0.0
 * */

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component

public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME="sub" ;
    private static final String CLAIM_KEY_CREATED="created";
//    JWT密钥和失效时间通过value注解拿
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

  /**
   * 根据用户信息生成token
   *
   * @param userDetails
   * @return
   * */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_USERNAME,new Date());
        return generateToken(claims);
    }
/**
 * @Author Jiawei
 * @Description //TODO
 * @version: v1.0.0
 * @Date 16:14 2022/2/23
 * @Param claims
 * @return
 **/

    private String generateToken(Map<String,Object> claims){
         return Jwts.builder()
                 .setClaims(claims)
                 .setExpiration(generateExpirationDate())
                 .signWith(SignatureAlgorithm.ES512, secret)
                 .compact();

    }
    /**
     * @Author Jiawei
     * @Description 从token中获取登录用户名
     * @version: v1.0.0
     * @Date 17:17 2022/2/23
     * @Param token
     * @return
     **/
    public  String getUserNameFromToken(String token){
        String username;
        try{
            Claims claims =getClaimFormToken(token);
            username =claims.getSubject();
        } catch (Exception e){
            username=null;
        }
        return username;
    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */

    private Claims getClaimFormToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        
        
    }

    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */

    public  boolean canRefresh(String token){
        return !isTokenExpired(token);

    }

    /**
     * 刷新token
     * @param token
     * @return
     */

    public String refreshToken(String token){
        Claims claims =getClaimFormToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     *  判断token是否失效
     * @param token
     * @return
     */

    private boolean isTokenExpired(String token) {
        Date expriDate = getExpiredDateFromToken(token);
        return expriDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */

    private Date getExpiredDateFromToken(String token) {
        Claims claims= getClaimFormToken(token);
        return claims.getExpiration();
    }

    /**
 * @Author Jiawei
 * @Description //生成token失效时间
 * @version: v1.0.0
 * @Date 16:08 2022/2/23
 * @Param
 * @return
 **/


    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
