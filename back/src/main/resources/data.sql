   INSERT INTO pix (
       pix_key, 
       pix_value, 
       name, 
       status, 
       qr_code_text, 
       qr_code_image, 
       created_at, 
       confirmed_at, 
       city, 
       state
   ) VALUES 
   ('exampleKey1', 100.00, 'Description 1', 'PENDING', 'QRCodeText1', 'QRCodeImage1', CURRENT_TIMESTAMP, NULL, 'City1', 'State1'),
   ('exampleKey2', 200.00, 'Description 2', 'CONFIRMED', 'QRCodeText2', 'QRCodeImage2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'City2', 'State2');