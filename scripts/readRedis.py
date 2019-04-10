import redis
# import cql
# import exceptions
from cassandra.cluster import Cluster
from cassandra.query import BatchStatement

cluster = Cluster(['localhost'])
session = cluster.connect('FluffyClientData')

session.execute("CREATE TYPE RedisData (data text, fileName text, userName text, sequenceNumber text)")
session.execute("CREATE TABLE storeClientPayload (key text PRIMARY KEY, data frozen<RedisData>)")

class Data(object):
    """docstring for ClassName"""
    """Creating an object for the data present in redis"""
    def __init__(self, data, fileName, userName, sequenceNumber):
        self.data = data
        self.fileName = fileName
        self.userName = userName
        self.sequenceNumber = sequenceNumber

user_track_stmt = session.prepare(
    "INSERT INTO storeClientPayload (key, data) VALUES (?, ?)")


# add the prepared statements to a batch
batch = BatchStatement()
batch.add(user_track_stmt,
  ["hendrix", "email changed", datetime.utcnow(), "test"])

# execute the batch


redisClient = redis.Redis(host='localhost', port=6379, db=0)
# list = []
# for key in r.scan_iter("*"):
#     # delete the key
#     print (r.get(key))

keys = redisClient.keys('*')
for key in keys:
	
    type = redisClient.type(key)
    # if type == KV:
    #     val = redis.get(key)
    # if type == "HASH":

    # vals = redisClient.hgetall(key)
    data = redisClient.hget(key,"data")
    fileName = redisClient.hget(key,"fileName")
    userName = redisClient.hget(key,"userName")
    sequenceNumber = redisClient.hget(key,"sequenceNumber")

    session.execute(user_track_stmt,[key,Data(data,fileName,userName,sequenceNumber)])
    print(vals)

    # if type == ZSET:
    #     vals = redis.zrange(key, 0, -1)