package com.soft.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.constants.RedisConstant;
import com.soft.base.entity.SecretKey;
import com.soft.base.service.SecretKeyService;
import com.soft.base.mapper.SecretKeyMapper;
import com.soft.base.utils.RSAUtil;
import com.soft.base.utils.SecurityUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
* @author cyq
* @description 针对表【sys_secret_key】的数据库操作Service实现
* @createDate 2024-11-26 16:19:51
*/
@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, SecretKey>
    implements SecretKeyService{

    private final SecretKeyMapper secretKeyMapper;

    private final RedisTemplate<String,Object> redisTemplate;

    private final RSAUtil rsaUtil;

    private final SecurityUtil securityUtil;

    public SecretKeyServiceImpl(SecretKeyMapper secretKeyMapper,
                                RedisTemplate<String,Object> redisTemplate,
                                RSAUtil rsaUtil,
                                SecurityUtil securityUtil) {
        this.secretKeyMapper = secretKeyMapper;
        this.redisTemplate = redisTemplate;
        this.rsaUtil = rsaUtil;
        this.securityUtil = securityUtil;
    }

    @Cacheable(cacheNames = "cyx::rsa::public", key = "#type")
    @Override
    public String getPublicKey(Integer type) {
        return secretKeyMapper.getPublicKey(type);
    }

    @Cacheable(cacheNames = "cyx::rsa::private", key = "#type")
    @Override
    public String getPrivateKey(Integer type) {
        return secretKeyMapper.getPrivateKey(type);
    }

    @Override
    public void generateKey(Integer type) throws NoSuchAlgorithmException {
        Map<String, String> generate = rsaUtil.generate();
        String privateKey = generate.get("privateKey");
        String publicKey = generate.get("publicKey");
        String username = securityUtil.getUserInfo().getUsername();
        secretKeyMapper.generateKey(type, privateKey, publicKey, username);

        Set<String> keys = new HashSet<>();
        keys.add(RedisConstant.RSA_PUBLIC_KEY + type);
        keys.add(RedisConstant.RSA_PRIVATE_KEY + type);
        redisTemplate.delete(keys);
    }
}




