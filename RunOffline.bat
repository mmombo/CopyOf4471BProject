start cmd /k "cd PublisherLayer & cd StockInfoService & cd target & java -jar stock-info-service.jar"
start cmd /k "cd PublisherLayer & cd VolumeOverTimeService & cd target & java -jar volume-over-time-service.jar"
start cmd /k "cd PublisherLayer & cd PriceOverTimeService & cd target & java -jar price-over-time-service.jar"
start cmd /k "cd PublisherLayer & cd TopVolumeService & cd target & java -jar top-volume-service.jar"
start cmd /k "cd PublisherLayer & cd CurrencyExchangeService & cd target & java -jar currency-exchange-service.jar"
start cmd /k "cd PublisherLayer & cd ServiceManager & cd target & java -jar service-manager.jar"
start cmd /k "cd PublisherLayer & cd DataManager & cd target & java -jar data-manager.jar"
start cmd /k "cd MessageLayer & cd MessageManager & cd target & java -jar message-layer.jar"
timeout 1 >nul
start cmd /k "cd PublisherLayer & cd ServiceRegistry & cd target & java -jar service-registry.jar offline"
start cmd /k "cd SubscriberLayer & cd target & java -jar subscriber-layer.jar"