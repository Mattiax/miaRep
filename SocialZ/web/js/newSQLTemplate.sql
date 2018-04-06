/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  MATTI
 * Created: 1-apr-2018
 */
SELECT hobby,ELENCOHOBBIES.email FROM ELENCOHOBBIES,PERSONA WHERE ELENCOHOBBIES.email=PERSONA.email AND permesso = 1 ORDER BY hobby;
