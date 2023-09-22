# feedback-flow

Esse repositório hospeda o código do primeiro desafio para o Bootcamp Cielo Dev - FullStack

## AWS

Esses passos partem do princípio que um usuário com as permissões adequadas para criar os tópicos e filas e fazer a inscrição dos mesmos já exista.

Passo a passo dos comandos utilizados para fazer o setup dos serviços necessários na AWS

> Para configurar o CLI utilize o comando `aws configure`

### Configurando filas SQS necessários

```bash
aws sqs create-queue --queue-name SuggestionQueue.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true
aws sqs create-queue --queue-name ComplimentQueue.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true
aws sqs create-queue --queue-name CritiqueQueue.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true
```

### Configurando tópicos SNS necessários

```bash
# Definindo atributo FifoTopic para que seja possível fazer a inscrição com as filas SQS
aws sns create-topic --name SuggestionTopic.fifo --attributes FifoTopic=true
aws sns create-topic --name ComplimentTopic.fifo --attributes FifoTopic=true
aws sns create-topic --name CritiqueTopic.fifo --attributes FifoTopic=true
```

## Comandos para fazer inscrição de filas SQS nos respectivos tópicos

```bash
aws sns subscribe --topic-arn arn:aws:sns:<region-id>:<user-id>:SuggestionTopic.fifo --protocol sqs --notification-endpoint arn:aws:sqs:<region-id>:<user-id>:SuggestionQueue.fifo
aws sns subscribe --topic-arn arn:aws:sns:<region-id>:<user-id>:ComplimentTopic.fifo --protocol sqs --notification-endpoint arn:aws:sqs:<region-id>:<user-id>:ComplimentQueue.fifo
aws sns subscribe --topic-arn arn:aws:sns:<region-id>:<user-id>:CritiqueTopic.fifo --protocol sqs --notification-endpoint arn:aws:sqs:<region-id>:<user-id>:CritiqueQueue.fifo
```
