package com.zk.sms.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Jwt token util.
 *
 * @author guoying
 * @since 2019 -10-28 23:31:05
 */

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
@Slf4j
public class JwtTokenUtil {

    /**
     * The constant CLAIM_KEY_USERNAME.
     */
    private static final String CLAIM_KEY_USERNAME = "sub";

    /**
     * The constant CLAIM_KEY_CREATED.
     */
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 请求头
     */
    private String tokenHead;

    /**
     * 请求头
     */
    private String tokenHeader;

    /**
     * 密钥.
     */
    private String secret;

    /**
     * 过期时间.
     */
    private Long expiration;

    /**
     * 生成令牌token.
     *
     * @param claims 数据声明
     * @return 令牌
     * @author guoying
     * @since 2019 /10/28
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 获取数据声明.
     *
     * @param token 令牌
     * @return 数据声明
     * @author guoying
     * @since 2019 /10/28
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 根据用户信息生产令牌 .
     *
     * @param userDetails 用户信息
     * @return 令牌
     * @author guoying
     * @since 2019 /10/29
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据用户名生成令牌.
     *
     * @param username 用户名
     * @return 令牌
     * @author guoying
     * @since 2019 /10/29
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 获取用户名.
     *
     * @param token 令牌
     * @return 用户名
     * @author guoying
     * @since 2019 /10/28
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            username = claimsFromToken.getSubject();
        } catch (Exception e) {
            log.error("Jwt token获取用户名错误:{}", token);
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     * @author guoying
     * @since 2019 /10/28
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     * @author guoying
     * @since 2019 /10/28
     */
    public String refreshToken(String token) {
        String refreshedToken = null;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            log.error("刷新Jwt token错误:{},{}", token, e.getMessage());
        }
        return refreshedToken;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     * @author guoying
     * @since 2019 /10/28
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
