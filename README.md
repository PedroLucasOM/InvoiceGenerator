<h1 align="center" width="100vw">
  <img alt="Logo: InvoiceGenerator" src="https://github.com/PedroLucasOM/InvoiceGenerator/blob/master/logo.png" />
</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-green.svg?cacheSeconds=2592000" />
  <img src="https://img.shields.io/badge/java-11-green.svg" />
  <img src="https://img.shields.io/badge/spring-2.4.5-green.svg" />
  <a href="https://github.com/PedroLucasOM/InvoiceGenerator#readme" target="_blank">
    <img alt="documentation" src="https://img.shields.io/badge/documentation-yes-green.svg" />
  </a>
  <a href="https://github.com/PedroLucasOM/InvoiceGenerator/graphs/commit-activity" target="_blank">
    <img alt="maintenance" src="https://img.shields.io/badge/maintained-yes-green.svg" />
  </a>
  <a href="https://twitter.com/PedroLucasOM" target="_blank">
    <img alt="Twitter: PedroLucasOM" src="https://img.shields.io/twitter/follow/PedroLucasOM.svg?style=social" />
  </a>
</p>

> :computer: Spring Batch Application to generate invoices based in clients, transactions and credit cards :credit_card:  inside of a configured database with Docker :whale:.

# Topics

1. [About SpringBatch](https://github.com/PedroLucasOM/InvoiceGenerator#1-about-springbatch)
2. [About the Project](https://github.com/PedroLucasOM/InvoiceGenerator#2-about-the-project)
    - [Implemented Job](https://github.com/PedroLucasOM/InvoiceGenerator#implemented-job)
    - [Prerequisites](https://github.com/PedroLucasOM/InvoiceGenerator#prerequisites)
    - [Run](https://github.com/PedroLucasOM/InvoiceGenerator#run)
    - [Usage](https://github.com/PedroLucasOM/InvoiceGenerator#usage)
    - [Stop](https://github.com/PedroLucasOM/InvoiceGenerator#stop)
3. [Author](https://github.com/PedroLucasOM/InvoiceGenerator#3-author)
4. [Contributing](https://github.com/PedroLucasOM/InvoiceGenerator#4-contributing-)
5. [Show your support](https://github.com/PedroLucasOM/InvoiceGenerator#5-show-your-support)
6. [License](https://github.com/PedroLucasOM/InvoiceGenerator#6-license-)


# 1. About SpringBatch

It is a framework that uses the Java Virtual Machine and the Spring Ecosystem to build batch applications. By definition, batch systems are systems that realize a process of a finite amount of data without interaction or interruption.

To learn more about this framework, view this article on the Notion:
[SpringBatch Article](https://www.notion.so/Spring-Batch-4cc5c3c22b9b49c58f6c4e23097c3c9a)

# 2. About the Project

## Implemented Job

It's responsible to execute two steps that will import credit cards and transactions from a .txt file to a configured Datasource. After it, it will gerenate multiples files to each credit card invoice with their respective transactions.

This is the file that will be imported:

- [data.txt](https://github.com/PedroLucasOM/InvoiceGenerator/blob/master/files/input/data.txt)

## Prerequisites

- docker

## Run

With the docker started, execute this command at the project root:

```sh
docker-compose up -d --build
```

## Usage

#### Seeing the valid records in the 

**Credit Card records:**

```sh
docker-compose exec database_app mysql -u root -papp#1234 -e "select * from app_batch.credit_card;"
```

**Transaction records:**

```sh
docker-compose exec database_app mysql -u root -papp#1234 -e "select * from app_batch.transaction;"
```

#### Seeing the invalid records in the generated file

Navigate to **/files/output/** inside of project root and open the files that contains **"invoice"** in their names. You will see the invoices layout and their transactions.

## Stop

To stop correctly:

```sh
docker-compose down -v
```

Remember to execute this command each time that you want change the parameter value.

# 3. Author

👤 **Pedro Lucas**

* Twitter: [@PedroLucasOM](https://twitter.com/PedroLucasOM)
* Github: [@PedroLucasOM](https://github.com/PedroLucasOM)
* LinkedIn: [@PedroLucasOM](https://linkedin.com/in/PedroLucasOM)

# 4. Contributing 🤝

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/PedroLucasOM/InvoiceGenerator/issues).

# 5. Show your support

Give a :star: if this project helped you!

# 6. License 📝

Copyright © 2021 [Pedro Lucas](https://github.com/PedroLucasOM). <br />

